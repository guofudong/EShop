package com.gfd.eshop.feature.settings;


import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.gfd.eshop.R;

/**
 * 设置Fragment
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

}
