package ydrasaal.alligregator.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import ydrasaal.alligregator.R;

/**
 * Created by lchazal on 07/09/15.
 *
 */
public class LoginActivity extends AToolbarCompatActivity {

    public static final String TAG = "LOGIN_ACTIVITY";

    public final int    PASSWORD_MINIMUM_SIZE = 6;
    public final int    LOGIN_MINIMUM_SIZE = 5;

    private EditText    idField;
    private EditText    passwordField;
    private Button      loginButton;
    private ProgressDialog  mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupToolbar(R.drawable.ic_navigation_back, "Login");

        initLoginButton();
        initIdField();
        initPasswordField();
    }

    @Override
    protected void onHomeButtonClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, FrontActivity.class));
        finish();
    }

    private void initLoginButton(){
        loginButton = (Button)findViewById(R.id.connect);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClicked();
            }
        });
    }


    private void initIdField() {

        idField = (EditText) findViewById(R.id.idField);
    }

    private void initPasswordField(){
        passwordField = (EditText) findViewById(R.id.passwordField);
    }


    private void switchToLobby() {
        startActivity(new Intent(this, LobbyActivity.class));
        finish();
    }

    private void onLoginButtonClicked() {

        startActivity(new Intent(this, LobbyActivity.class));

//        SurfinAPIRetrofit.INSTANCE.logIn(
//                idField.getText().toString(),
//                passwordField.getText().toString(),
//                createLogInAPICallback());

    }

//    private APICallbackListener<RetrofitToken> createLogInAPICallback() {
//        return new APICallbackListener<RetrofitToken>() {
//            @Override
//            public void onResponse(Response<RetrofitToken> response) {
//                if (response.isSuccess()) {
//                    Log.d(TAG, "Successful login");
//
//                    SurfinAPIRetrofit.INSTANCE.getCurrentUser(createUserAPICallback());
//                }
//            }
//
//            @Override
//            public void onFailure() {
//                Log.d(TAG, "Unsuccessful login");
//                displayAuthAlertDialog();
//            }
//        };
//    }



//    private APICallbackListener<RealmUser> createUserAPICallback() {
//        return new APICallbackListener<RealmUser>() {
//            @Override
//            public void onResponse(Response<RealmUser> response) {
//                if (response.isSuccess()) {
//                    onLoginSuccess(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure() {
//                displayNetworkAlertDialog();
//            }
//        };
//    }

//    public void onLoginSuccess(RealmUser response) {
//        Me.getInstance().setNewUserData(LoginActivity.this, response);
//
//        registerRefreshToken();
//
//        logUserToCrashlytics();
//        logUserToIntercom();
//
//        if (mDialog != null) mDialog.dismiss();
//        switchToLobby();
//    }

//    private void registerRefreshToken() {
//        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_pref_session_file), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(getString(R.string.shared_pref_refresh_token), Session.getInstance().getRefreshToken());
//        editor.putBoolean(getString(R.string.shared_pref_logged_in), true);
//        editor.apply();
//        Log.d("REFRESH_TOKEN", "Registered token : " + Session.getInstance().getRefreshToken());
//    }

}
