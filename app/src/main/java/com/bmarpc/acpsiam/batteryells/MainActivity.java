package com.bmarpc.acpsiam.batteryells;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bmarpc.acpsiam.batteryells.fragments.FragmentBatterYell;
import com.bmarpc.acpsiam.batteryells.fragments.FragmentBatteryInfo;
import com.bmarpc.acpsiam.batteryells.fragments.PreferenceScreenFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //*Finding IDs
        bottomNavigationView = findViewById(R.id.bottom_navigation_view_id);
        toolbar = findViewById(R.id.toolbar_id);



        setSupportActionBar(toolbar);

        selectFragment(new FragmentBatterYell());
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_batteryell_id);
        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.bottom_menu_settings_id) {
                            selectFragment(new PreferenceScreenFragment());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.dev_menu_option_id) {
            startActivity(new Intent(MainActivity.this, DevActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout_id, fragment);
        fragmentTransaction.commit();
    }

}
