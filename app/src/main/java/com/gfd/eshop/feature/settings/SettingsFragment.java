package com.gfd.eshop.feature.settings;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.gfd.eshop.R;

public class SettingsFragment extends PreferenceFragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
