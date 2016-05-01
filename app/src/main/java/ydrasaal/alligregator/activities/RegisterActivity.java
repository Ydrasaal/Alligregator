package ydrasaal.alligregator.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ydrasaal.alligregator.R;

/**
 * Created by lchazal on 08/09/15.
 *
 */
public class RegisterActivity extends AppCompatActivity {

    private Button      registerButton;

    private boolean mLogInInProgress = false;

    private ProgressDialog  registeringProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupView();

        initIdField();
        initPasswordField();
        initEmailField();
        initRegisterButton();
    }

    private void    setupView() {
        View v = getLayoutInflater().inflate(R.layout.activity_register, null);
        setContentView(v);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, FrontActivity.class));
        finish();
    }

    private void    initIdField() {
        EditText idField = (EditText) findViewById(R.id.idField);
    }

    private void    initPasswordField() {
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
    }

    private void    initEmailField() {
        EditText emailField = (EditText) findViewById(R.id.emailField);
    }

    private void    initRegisterButton() {
        registerButton = (Button) this.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this, LobbyActivity.class));
//                toggleEditionEnabled(false);
//                registeringProgress = AlertDialogFactory.createProgressDialog(RegisterActivity.this);
//                registeringProgress.setCancelable(false);
//                registeringProgress.show();
//
//                registerUser();
//            }
//        });
            }
        });
    }

    private void registerUser() {
//        SurfinAPIRetrofit.INSTANCE.registerUser(
//                emailField.getText().toString(),
//                getDeviceLocalisationISO(),
//                passwordField.getText().toString(),
//                idField.getText().toString(),
//                RegisterActivity.this);
    }

//    private APICallbackListener<RetrofitToken>    createLoginListener() {
//        APICallbackListener<RetrofitToken> listener = new APICallbackListener<RetrofitToken>() {
//            @Override
//            public void onResponse(Response<RetrofitToken> response) {
//                Log.d("RegisterActivity", "Login successful !");
//                switchToLobby();
//            }
//
//            @Override
//            public void onFailure() {
//                displayAlertDialog();
//            }
//        };
//        return listener;
//    }

    private void switchToLobby() {
//        if (mLogInInProgress || mUploadingAvatarInProgress) return;
//
//        if (registeringProgress != null) registeringProgress.dismiss();
//
//        startActivity(new Intent(this, LobbyActivity.class));
//        finish();
    }


    private void    showImportErrorToast() {
//        Toast.makeText(this, getString(R.string.error_opening_file), Toast.LENGTH_LONG).show();
    }

    private void    logIn() {
//        SurfinAPIRetrofit.INSTANCE.logIn(
//                idField.getText().toString(),
//                passwordField.getText().toString(),
//                new APICallbackListener<RetrofitToken>() {
//                    @Override
//                    public void onResponse(Response<RetrofitToken> response) {
//                        if (response.isSuccess()) {
//                            Log.d("REGISTER", "Log in success");
//                            uploadAvatar();
//                            getUserProfile();
//                        } else {
//                            displayAlertDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        displayAlertDialog();
//                    }
//                });
    }

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
//                displayAlertDialog();
//            }
//        };
//    }

//    public void onLoginSuccess(RealmUser response) {
//        Me.getInstance().setNewUserData(RegisterActivity.this, response);
//
//        registerRefreshToken();
//
//        logUserToCrashlytics();
//        logUserToIntercom();
//
//        if (registeringProgress != null) registeringProgress.dismiss();
//        switchToLobby();
//    }

//    @Override
//    public void onResponse(Response<ErrorReport> response) {
//        if (response.isSuccess()) {
//            Log.d("REGISTER", "Register success");
//            logIn();
//        }
//        else {
//            displayAlertDialog();
//        }
//    }
//
//    @Override
//    public void onFailure() {
//        toggleEditionEnabled(true);
//        displayAlertDialog();
//    }

}