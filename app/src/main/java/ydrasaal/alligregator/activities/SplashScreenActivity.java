package ydrasaal.alligregator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import ydrasaal.alligregator.R;

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

//        User user = new User();
//        user.setUsername("undeuxtrois");
//        user.setPassword("123456");
//        user.setMail("kjdgh@jhdfg.fr");
//
////        AlligregatorRetrofitAPI.pass = user.getPassword();
////        AlligregatorRetrofitAPI.username = user.getUsername();
//
//        AlligregatorAPI.getInstance().subscribeToRSS(new APICallbackListener<Void>() {
//            @Override
//            public void onResponseSuccess(Response<Void> response) {
//                AlligregatorAPI.getInstance().getSubscriptions(new APICallbackListener<Void>() {
//                    @Override
//                    public void onResponseSuccess(Response<Void> response) {
//                        Log.d("JHDGF", "SUCCESS " + response.message());
//                    }
//
//                    @Override
//                    public void onResponseFailure() {
//                        Log.d("JHDGF", "response failure");
//
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        Log.d("JHDGF", "call failure");
//
//                    }
//                });
//            }
//
//            @Override
//            public void onResponseFailure() {
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        }, "http://shemalensfw.tumblr.com/rss");


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
        startActivity(new Intent(this, FrontActivity.class));
//        startActivity(new Intent(this, LobbyActivity.class));
        finish();
    }
}
