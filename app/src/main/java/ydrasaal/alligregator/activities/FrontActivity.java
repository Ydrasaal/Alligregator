package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ydrasaal.alligregator.R;

/**
 * Created by lchazal on 09/09/15.
 *
 */
public class FrontActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        Button logBtn = (Button) findViewById(R.id.btn_log);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontActivity.this, LoginActivity.class));
                finish();
            }
        });
        Button subBtn = (Button) findViewById(R.id.btn_sub);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}
