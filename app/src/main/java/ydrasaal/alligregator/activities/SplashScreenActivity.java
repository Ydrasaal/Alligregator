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
 *
 * Simple Splashscreen
 */
public class SplashScreenActivity extends AppCompatActivity {

    public static final int SPLASH_STOP = 0;
    public static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setSplashScreenCountDown();
    }

    /**
     * Create and starts countdown before the app starts
     *
     * (This Handler doesn't use an external Service, invalidating the HandlerLeak Lint warning)
     */
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
