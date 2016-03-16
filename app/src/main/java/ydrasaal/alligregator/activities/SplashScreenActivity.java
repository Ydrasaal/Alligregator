package ydrasaal.alligregator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Set;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.utils.SharedPrefUtils;

/**
 * Created by Léo on 12/03/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {

    public static final int SPLASH_STOP = 0;
    public static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Set<String> set = SharedPrefUtils.getString(this);
        if (set == null) {
            Log.d("SHARED_PREF", "Null preferences ...");
        } else if (set.isEmpty()){
            Log.d("SHARED_PREF", "Empty preferences ...");
        } else {
            Log.d("SHARED_PREF", "Got " + set.size() + " saved url(s)");
            int i = 1;
            for (String s :
                    set) {
                Log.d("SHARED_PREF", i++ + ": " + s);
            }
        }

        setSplashScreenCountDown();
    }

    private void setSplashScreenCountDown() {
        Handler _splashHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SPLASH_STOP:
                        switchToLobby();
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
        Message msg = new Message();
        msg.what = SPLASH_STOP;
        _splashHandler.sendMessageDelayed(msg, SPLASH_TIME);
    }

    private void switchToLobby() {
        startActivity(new Intent(this, LobbyActivity.class));
        finish();
    }
}
