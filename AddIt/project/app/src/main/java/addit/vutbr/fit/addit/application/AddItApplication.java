package addit.vutbr.fit.addit.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by david on 26.10.16.
 */

public class AddItApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // init the realm database
        Realm.init(this);
    }
}
