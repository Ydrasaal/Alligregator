package ydrasaal.alligregator.utils;

import android.app.Activity;

import ydrasaal.alligregator.R;

/**
 * Created by lchazal on 17/03/16.
 *
 * Animation helper for activities transitions
 */
public class AnimationUtils {

    public static void animateIn(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public static void animateOut(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
