package com.bmarpc.acpsiam.batteryells.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.bmarpc.acpsiam.batteryells.R;

public class FragmentSettings extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey);

    }
}