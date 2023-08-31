package com.bmarpc.acpsiam.batteryells.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bmarpc.acpsiam.batteryells.BatteryService;
import com.bmarpc.acpsiam.batteryells.R;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.slider.Slider;

import java.util.Objects;

public class FragmentBatterYell extends Fragment {

    private final Handler handlerBatteryCharged = new Handler();
    private final Handler handlerBatteryLow = new Handler();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent serviceIntent;
    MaterialSwitch batteryServiceSwitch, batteryChargedAlarmSwitch, batteryLowAlarmSwitch;
    Slider chargedAlarmSlider, lowAlarmSlider;
    TextView chargedPercentageTextView, lowPercentageTextView;
    LinearLayout batteryChargedAlarmExtraLayout, batteryLowAlarmExtraLayout, batteryServiceExtraLayout;
    private LottieAnimationView batteryChargedLoadingLottie, batteryLowLoadingLottie;
    private int permissionDenialCount = 0;


    public FragmentBatterYell() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_batter_yell, container, false);


        //*Finding IDs
        batteryServiceSwitch = v.findViewById(R.id.battery_service_switch_id);
        batteryChargedAlarmSwitch = v.findViewById(R.id.battery_charged_alarm_switch_id);
        chargedAlarmSlider = v.findViewById(R.id.slider_charged_alarm_id);
        batteryLowAlarmSwitch = v.findViewById(R.id.battery_low_alarm_switch_id);
        lowAlarmSlider = v.findViewById(R.id.slider_low_alarm_id);
        chargedPercentageTextView = v.findViewById(R.id.charged_percentage_textview_id);
        lowPercentageTextView = v.findViewById(R.id.low_percentage_textview_id);
        batteryLowAlarmExtraLayout = v.findViewById(R.id.battery_low_alarm_extra_layout_id);
        batteryChargedAlarmExtraLayout = v.findViewById(R.id.battery_charged_alarm_extra_layout_id);
        batteryServiceExtraLayout = v.findViewById(R.id.battery_service_enabled_extra_layout_id);
        batteryChargedLoadingLottie = v.findViewById(R.id.battery_charged_alarm_preference_save_loading_lottie_id);
        batteryLowLoadingLottie = v.findViewById(R.id.battery_low_alarm_preference_save_loading_lottie_id);


        sharedPreferences = requireContext().getSharedPreferences(getString(R.string.SHARED_PREFERENCE_MAIN), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        serviceIntent = new Intent(requireContext(), BatteryService.class);
        initializeValues();


        batteryChargedAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                batteryChargedAlarmExtraLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                editor.putBoolean(getString(R.string.BATTERY_CHARGED_ALARM_ON_BOOL), isChecked);
                editor.apply();
            }
        });
        batteryLowAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                batteryLowAlarmExtraLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                editor.putBoolean(getString(R.string.BATTERY_LOW_ALARM_ON_BOOL), isChecked);
                editor.apply();
            }
        });


        chargedAlarmSlider.addOnChangeListener((slider, value, fromUser) -> {
            animatePercentageChange(chargedPercentageTextView, (int) value);
            editor.putInt(getString(R.string.BATTERY_CHARGED_ALARM_INT), (int) value);
            editor.apply();

            batteryChargedLoadingLottie.setVisibility(View.VISIBLE);

            // Remove any pending callbacks before posting a new one
            handlerBatteryCharged.removeCallbacksAndMessages(null);

            // Post a delayed action
            handlerBatteryCharged.postDelayed(() -> {
                // Call your method here after a 3-second delay
                stopBatteryService();
                startBatteryService();
                batteryChargedLoadingLottie.setVisibility(View.GONE);
            }, 2000); // 2000 milliseconds = 2 seconds

        });

        lowAlarmSlider.addOnChangeListener((slider, value, fromUser) -> {
            animatePercentageChange(lowPercentageTextView, (int) value);
            editor.putInt(getString(R.string.BATTERY_LOW_ALARM_INT), (int) value);
            editor.apply();


            batteryLowLoadingLottie.setVisibility(View.VISIBLE);
            // Remove any pending callbacks before posting a new one
            handlerBatteryLow.removeCallbacksAndMessages(null);

            // Post a delayed action
            handlerBatteryLow.postDelayed(() -> {
                // Call your method here after a 3-second delay
                stopBatteryService();
                startBatteryService();
                batteryLowLoadingLottie.setVisibility(View.GONE);
            }, 2000); // 2000 milliseconds = 2 seconds

        });


        if (!isMyServiceRunning(BatteryService.class)) {
            editor.putBoolean(getString(R.string.BATTERY_SERVICE_ON_BOOL), false).apply();
        }
        batteryServiceSwitch.setChecked(sharedPreferences.getBoolean(getString(R.string.BATTERY_SERVICE_ON_BOOL), false));
        batteryServiceSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startBatteryService();
                batteryServiceExtraLayout.setVisibility(View.VISIBLE);
            } else {
                stopBatteryService();
                batteryServiceExtraLayout.setVisibility(View.GONE);
            }
        });

        return v;
    }

    private void animatePercentageChange(TextView textView, int newValue) {
        int oldValue = Integer.parseInt(textView.getText().toString().replace("%", ""));

        ValueAnimator animator = ValueAnimator.ofInt(oldValue, newValue);
        animator.setDuration(500); // Animation duration in milliseconds

        animator.addUpdateListener(valueAnimator -> {
            int animatedValue = (int) valueAnimator.getAnimatedValue();
            String percentage = animatedValue + "%";
            textView.setText(percentage);
        });

        animator.start();
    }


    private final ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                if (Boolean.TRUE.equals(result.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false))) {
                    // Permission granted, start the BatteryService
                    startBatteryService();
                    permissionDenialCount = 0;
                } else {
                    permissionDenialCount++;

                    if (permissionDenialCount > 0) {
                        Toast.makeText(requireContext(), "Please allow notifications to start the service", Toast.LENGTH_SHORT).show();
                        batteryServiceSwitch.setChecked(false);

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", requireActivity().getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }
    );

    private void startBatteryService() {
        if (notificationAllowed()) {
            // Start the BatteryService
            requireContext().startService(serviceIntent);
            editor.putBoolean(getString(R.string.BATTERY_SERVICE_ON_BOOL), true);
            editor.apply();
        } else {
            allowNotificationDialog();
        }
    }

    private boolean notificationAllowed() {
        boolean isAllowed;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            isAllowed = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            isAllowed = true;
        }
        return isAllowed;
    }

    private void stopBatteryService() {
        if (isMyServiceRunning(BatteryService.class)) {
            requireContext().stopService(serviceIntent);
            editor.putBoolean(getString(R.string.BATTERY_SERVICE_ON_BOOL), false);
            editor.apply();
        }
    }

    public void allowNotificationDialog() {

        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_allow_notification);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);


        Button dialogButton = (Button) dialog.findViewById(R.id.allow_notification_dialog_ok_button_id);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(new String[]{Manifest.permission.POST_NOTIFICATIONS});
                }
            }
        });

        dialog.show();

    }

    // Method to check if a specific service class is running
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) requireContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void initializeValues() {
        int preferredChargedAlarmPercentage = sharedPreferences.getInt(getString(R.string.BATTERY_CHARGED_ALARM_INT), 90);
        int preferredLowBatteryAlarmPercentage = sharedPreferences.getInt(getString(R.string.BATTERY_LOW_ALARM_INT), 25);
        boolean batteryLowAlarmOn = sharedPreferences.getBoolean(getString(R.string.BATTERY_LOW_ALARM_ON_BOOL), false);
        boolean batteryChargedAlarmOn = sharedPreferences.getBoolean(getString(R.string.BATTERY_CHARGED_ALARM_ON_BOOL), false);
        boolean batteryServiceOn = isMyServiceRunning(BatteryService.class);

        chargedPercentageTextView.setText(String.valueOf(preferredChargedAlarmPercentage) + "%");
        lowPercentageTextView.setText(String.valueOf(preferredLowBatteryAlarmPercentage) + "%");
        chargedAlarmSlider.setValue(preferredChargedAlarmPercentage);
        lowAlarmSlider.setValue(preferredLowBatteryAlarmPercentage);

        batteryServiceExtraLayout.setVisibility(batteryServiceOn ? View.VISIBLE : View.GONE);
        batteryLowAlarmExtraLayout.setVisibility(batteryLowAlarmOn ? View.VISIBLE : View.GONE);
        batteryChargedAlarmExtraLayout.setVisibility(batteryChargedAlarmOn ? View.VISIBLE : View.GONE);

        batteryLowAlarmSwitch.setChecked(batteryLowAlarmOn);
        batteryChargedAlarmSwitch.setChecked(batteryChargedAlarmOn);
    }



}