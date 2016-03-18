package ydrasaal.alligregator.utils;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

/**
 * Created by Léo on 12/03/2016.
 */
public class ColorUtils {

    public static ColorUtils getInstance() {
        if (instance == null) {
            instance = new ColorUtils();
        }
        return instance;
    }


    private ColorUtils() {
        evaluator = new ArgbEvaluator();
    }

    private static ColorUtils instance;

    private ArgbEvaluator evaluator;
    private int startingColor;
    private int endingColor;

    /**
     * Returns the color value between the start and end color depending on the progression
     *
     * @return color value as 32 bits int
     */
    public int getColorProgression(float progression) {
        return (int) evaluator.evaluate(progression, startingColor, endingColor);
    }

    public void setStartingColor(int startingColor) {
        this.startingColor = startingColor;
    }

    public void setEndingColor(int endingColor) {
        this.endingColor = endingColor;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }
}
