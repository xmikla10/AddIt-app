package addit.vutbr.fit.addit.view;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;

import addit.vutbr.fit.addit.R;

public class AppPreferences extends AddItBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }


    public static class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        Locale myLocale;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);

            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
            CheckBoxPreference checkbox = (CheckBoxPreference) findPreference("notification");
            if (checkbox.isChecked() == false)
                checkbox.setSummary(R.string.notifOff);

            checkbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    if (checkbox.isChecked()) {
                        checkbox.setSummary(R.string.notifOn);
                        Toast.makeText(getActivity(), R.string.notifOn, Toast.LENGTH_SHORT).show();
                    } else if (checkbox.isChecked() == false) {
                        checkbox.setSummary(R.string.notifOff);
                        Toast.makeText(getActivity(), R.string.notifOff, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            ListPreference listPreference = (ListPreference) findPreference("language");
            if (listPreference.getValue() == null) {
                listPreference.setValueIndex(0);
            }
            listPreference.setSummary(listPreference.getValue().toString());
//            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
//            {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    preference.setSummary(((ListPreference) preference).getEntry());
////                    Toast.makeText(getActivity(), "Set language : "+newValue.toString() ,
////                            Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "Set language : " + newValue.toString() + " " + ((ListPreference) preference).getEntry(),
//                            Toast.LENGTH_SHORT).show();
//                    setLanguage(newValue.toString());
//                    return true;
//                }
//
//
//
//            });
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            if ("language".equals(s)) {
                String lang = sharedPreferences.getString("language", "en_US");
                setLanguage(lang);
            }
        }

        public void setLanguage(String lang) {
            Locale myLocale = new Locale(lang);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }

}
