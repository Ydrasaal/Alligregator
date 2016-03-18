package ydrasaal.alligregator.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.network.chrome.CustomTabActivityHelper;

/**
 * Created by lchazal on 18/03/16.
 */
public class ChromeUtils {

    private static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";

    public static void openUrlInCustomTab(Activity activity, Uri uri) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        intentBuilder.setStartAnimations(activity, R.anim.in_from_right, R.anim.out_to_left);
        intentBuilder.setExitAnimations(activity, R.anim.in_from_left, R.anim.out_to_right);
        intentBuilder.setShowTitle(true);

        CustomTabsIntent customTabsIntent = intentBuilder.build();
        CustomTabActivityHelper.openCustomTab(activity, customTabsIntent, uri,
                new CustomTabActivityHelper.CustomTabFallback() {
                    @Override
                    public void openUri(Activity activity, Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        activity.startActivity(intent);
                    }
                });
    }
}
