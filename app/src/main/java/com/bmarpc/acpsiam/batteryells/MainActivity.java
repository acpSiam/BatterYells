package com.bmarpc.acpsiam.batteryells;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bmarpc.acpsiam.batteryells.otherclasses.LanguageHelper;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferenceEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //*Set the language based on user's previous preference
        LanguageHelper.INSTANCE.setLocale(this, LanguageHelper.INSTANCE.getCurrentLocalePref(this));




        //*Initializing important Shared Preferences
        sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFERENCES_APP_PROCESS), MODE_PRIVATE);
        sharedPreferenceEditor = sharedPreferences.edit();

        //Currently chosen Theme by user
        String currentTheme = sharedPreferences.getString(getString(R.string.SELECTED_THEME_COLOR),
                getString(R.string.PREFERRED_COLOR_BLUE));


            switch (currentTheme) {
                case "CYAN":
                    setTheme(R.style.AppTheme_Blue); // Apply the dark version of the "CYAN" theme
                    break;
                case "PURPLE":
                    setTheme(R.style.AppTheme_Purple); // Apply the dark version of the "PURPLE" theme
                    break;
                case "LIME":
                    setTheme(R.style.AppTheme_Lime); // Apply the dark version of the "LIME" theme
                    break;
                case "CORAL":
                    setTheme(R.style.AppTheme_Coral); // Apply the dark version of the "CORAL" theme
                    break;
                case "VIOLET":
                    setTheme(R.style.AppTheme_Violet); // Apply the dark version of the "VIOLET" theme
                    break;
                case "INDIGO":
                    setTheme(R.style.AppTheme_Indigo); // Apply the dark version of the "INDIGO" theme
                    break;
                case "MINT_GREEN":
                    setTheme(R.style.AppTheme_Mint_Green); // Apply the dark version of the "GREEN" theme
                    break;
                case "GOLDEN_YELLOW":
                    setTheme(R.style.AppTheme_Golden_Yellow); // Apply the dark version of the "YELLOW" theme
                    break;
                case "ORANGE":
                    setTheme(R.style.AppTheme_Orange); // Apply the dark version of the "ORANGE" theme
                    break;
                case "RED":
                    setTheme(R.style.AppTheme_Red); // Apply the dark version of the "RED" theme
                    break;
                default:
                    // Default theme if the color name is not recognized
                    setTheme(R.style.AppTheme_Blue);
                    break;

            }

        setContentView(R.layout.activity_main);


        //*Finding IDs
        bottomNavigationView = findViewById(R.id.bottom_navigation_view_id);
        toolbar = findViewById(R.id.toolbar_id);



        setSupportActionBar(toolbar);




        selectFragment(new FragmentBatterYell());
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

        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_batteryell_id);


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
