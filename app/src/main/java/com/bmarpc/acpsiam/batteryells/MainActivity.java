package com.bmarpc.acpsiam.batteryells;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bmarpc.acpsiam.batteryells.fragments.FragmentBatterYell;
import com.bmarpc.acpsiam.batteryells.fragments.FragmentBatteryInfo;
import com.bmarpc.acpsiam.batteryells.fragments.FragmentBatteryUsage;
import com.bmarpc.acpsiam.batteryells.fragments.FragmentSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //*Finding IDs
        bottomNavigationView = findViewById(R.id.bottom_navigation_view_id);




        selectFragment(new FragmentBatterYell());
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_batteryell_id);
        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.bottom_menu_settings_id) {
                            selectFragment(new FragmentSettings());
                            return true;
                        } else if (itemId == R.id.bottom_menu_batteryell_id) {
                            selectFragment(new FragmentBatterYell());
                            return true;
                        } else if (itemId == R.id.bottom_menu_battery_info_id) {
                            selectFragment(new FragmentBatteryInfo());
                            return true;
                        }
                        return false;
                    }
                }
        );
    }


    public void selectFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout_id, fragment);
        fragmentTransaction.commit();
    }

}
