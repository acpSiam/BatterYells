package com.bmarpc.acpsiam.batteryells;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.bmarpc.acpsiam.batteryells.fragments.FragmentBatteryInfo;

public class BatteryInfoReceiver extends BroadcastReceiver {
    String healthLbl, pluggedLbl, statusLbl, technology;
    float temp;
    int voltage, batteryPct;
    float capacity;

    private FragmentBatteryInfo fragmentBatteryInfo; // Reference to the fragment

    public BatteryInfoReceiver(FragmentBatteryInfo fragmentBatteryInfo) {
        this.fragmentBatteryInfo = fragmentBatteryInfo;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                healthLbl = "Cold";
                break;

            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthLbl = "Dead";
                break;

            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthLbl = "Good";
                break;

            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthLbl = "Over Voltage";
                break;

            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthLbl = "Overheat";
                break;

            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthLbl = "Unspecified Failure";
                break;

            default:
                healthLbl = "Unknown";
                break;
        }


        // Calculate Battery Percentage ...
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (level != -1 && scale != -1) {
            batteryPct = (int) ((level / (float) scale) * 100f);
        }


        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                pluggedLbl = "Wireless Charging";
                break;

            case BatteryManager.BATTERY_PLUGGED_USB:
                pluggedLbl = "USB Charging";
                break;

            case BatteryManager.BATTERY_PLUGGED_AC:
                pluggedLbl = "AC Charging";
                break;

            default:
                pluggedLbl = "Not Charging";
                break;
        }


        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusLbl = "Charging";
                break;

            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusLbl = "Discharging";
                break;

            case BatteryManager.BATTERY_STATUS_FULL:
                statusLbl = "Full";
                break;

            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                statusLbl = "Unknown";
                break;

            default:
                statusLbl = "Not Charging";
                break;
        }


        if (intent.getExtras() != null) {
            technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        }

        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

        if (temperature > 0) {
            temp = ((float) temperature) / 10f; //In Celsius
        }

        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0); //In mV

        capacity = (float) getBatteryCapacity(context); //In mAh


        fragmentBatteryInfo.updateBatteryUI(batteryPct, pluggedLbl, healthLbl, temp);
    }




    public double getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return batteryCapacity;
    }








}
