package addit.vutbr.fit.addit.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.Locale;

import addit.vutbr.fit.addit.lib.ExceptionHandler;

/**
 * Created by david on 4.11.16.
 */

public abstract class AddItBaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set exception handler that prints info to log
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
    }


    public void setLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
