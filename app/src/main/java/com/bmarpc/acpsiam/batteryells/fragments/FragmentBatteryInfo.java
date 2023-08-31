package com.bmarpc.acpsiam.batteryells.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bmarpc.acpsiam.batteryells.BatteryInfoReceiver;
import com.bmarpc.acpsiam.batteryells.R;

public class FragmentBatteryInfo extends Fragment {

    TextView batteryLevelPercentageTextView,
//            batteryServiceRunningTextView,
pluggedStatusTextView,
            healthTextView,
            batteryTemperatureTextView,
    batteryTechnologyTextView,
    batteryCapacityTextView,
    batteryVoltageTextview
    ;
    BatteryInfoReceiver batteryInfoReceiver;
    LottieAnimationView batteryLevelProgressLottie, batteryChargingIndicatorLottie;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public FragmentBatteryInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_battery_info, container, false);

        //*Finding IDs
        batteryLevelProgressLottie = v.findViewById(R.id.battery_level_progress_lottie_id);
        batteryLevelPercentageTextView = v.findViewById(R.id.battery_level_percentage_textview_id);
//        batteryServiceRunningTextView = v.findViewById(R.id.battery_service_running_textview_id);
        pluggedStatusTextView = v.findViewById(R.id.plugged_status_textview_id);
        healthTextView = v.findViewById(R.id.battery_health_textview_id);
        batteryTemperatureTextView = v.findViewById(R.id.battery_temperature_textview_id);
        batteryTechnologyTextView = v.findViewById(R.id.battery_technology_textview_id);
        batteryCapacityTextView = v.findViewById(R.id.battery_capacity_textview_id);
        batteryVoltageTextview = v.findViewById(R.id.battery_voltage_textview_id);
        batteryChargingIndicatorLottie = v.findViewById(R.id.charging_indicator_lottie_id);


        // Initialize SharedPreference
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.SHARED_PREFERENCE_MAIN), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        // Initialize the BatteryInfoReceiver
        batteryInfoReceiver = new BatteryInfoReceiver(FragmentBatteryInfo.this);

        // Register battery receiver
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getContext().registerReceiver(batteryInfoReceiver, filter);


        animateBatteryLevel();
//        batteryServiceRunningTextView.setText(sharedPreferences.getBoolean(getString(R.string.BATTERY_SERVICE_ON_BOOL), false) ? "Yes" : "No");


        return v;
    }


    public void updateBatteryUI(int batteryLevel, String pluggedState, String health, float temperature,
                                String technology, String capacity, String voltage, String statusLbl) {
        if (isAdded()) {
            requireActivity().runOnUiThread(() -> {
                if (!batteryLevelPercentageTextView.getText().toString().equals(batteryLevel + "%")) {
                    animateTextChange(batteryLevelPercentageTextView, batteryLevel + "%");
                }

                if (!pluggedStatusTextView.getText().toString().equals(pluggedState)) {
                    animateTextChange(pluggedStatusTextView, pluggedState);
                }

                if (!healthTextView.getText().toString().equals(health)) {
                    animateTextChange(healthTextView, health);
                }

                if (!batteryTemperatureTextView.getText().toString().equals(temperature + "° C")) {
                    animateTextChange(batteryTemperatureTextView, temperature + "° C");
                }

                if (!batteryTechnologyTextView.getText().toString().equals(technology)) {
                    animateTextChange(batteryTechnologyTextView, technology);
                }

                if (!batteryCapacityTextView.getText().toString().equals(capacity + " mAh")) {
                    animateTextChange(batteryCapacityTextView, capacity + " mAh");
                }

                if (!batteryVoltageTextview.getText().toString().equals(voltage + " mV")) {
                    animateTextChange(batteryVoltageTextview, voltage + " mV");
                }

                batteryChargingIndicatorLottie.setVisibility("Charging".equals(statusLbl) ? View.VISIBLE : View.GONE);

                batteryLevelProgressLottie.setProgress(batteryLevel / 100f);
            });
        }
    }


    private void animateBatteryLevel() {
        BatteryManager mBatteryManager = (BatteryManager) getContext().getSystemService(Context.BATTERY_SERVICE);
        int batteryLevel = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        ValueAnimator animator = ValueAnimator.ofInt(0, batteryLevel);
        animator.setDuration(1500); // Animation duration in milliseconds

        animator.addUpdateListener(valueAnimator -> {
            int animatedValue = (int) valueAnimator.getAnimatedValue();
            batteryLevelPercentageTextView.setText(animatedValue + "%");
            batteryLevelProgressLottie.setProgress(animatedValue / 100f);
        });

        animator.start();
    }

    private void animateTextChange(final TextView textView, final String newText) {
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(250); // Animation duration in milliseconds

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(newText); // Set new text
                textView.clearAnimation();

                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(250); // Animation duration in milliseconds
                textView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        textView.startAnimation(fadeOut);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(batteryInfoReceiver);
    }
}