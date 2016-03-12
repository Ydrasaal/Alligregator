package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ydrasaal.alligregator.FeedListActivity;
import ydrasaal.alligregator.R;

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
        startActivity(new Intent(this, FeedListActivity.class));
        finish();
    }
}
