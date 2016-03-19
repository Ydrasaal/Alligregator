package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.utils.AnimationUtils;

/**
 * Created by lchazal on 18/03/16.
 *
 * Display a few infos about the app
 */
public class AboutActivity extends AToolbarCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setupToolbar(R.drawable.ic_navigation_back, getString(R.string.about_title));

        try {
            ((TextView) findViewById(R.id.version_value)).setText(String.format(getString(R.string.about_version), getPackageManager().getPackageInfo(getPackageName(), 0).versionName));
            ((TextView) findViewById(R.id.build_value)).setText(String.format(getString(R.string.about_build), getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData.get("buildCount")));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    protected void onHomeButtonClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        AnimationUtils.animateOut(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }
}
