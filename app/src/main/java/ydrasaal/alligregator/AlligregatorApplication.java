package ydrasaal.alligregator;

import android.app.Application;

import ydrasaal.alligregator.utils.OptionsManager;

/**
 * Created by lchazal on 18/03/16.
 *
 * Application class. Handles app's initialisation
 */
public class AlligregatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OptionsManager.getInstance().retrieveOptionsFromSharedPreferences(this);
    }
}
