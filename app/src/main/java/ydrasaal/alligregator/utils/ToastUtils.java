package ydrasaal.alligregator.utils;

import android.content.Context;
import android.widget.Toast;

import ydrasaal.alligregator.R;

/**
 * Created by lchazal on 18/03/16.
 */
public class ToastUtils {

    public static void displayToast(Context context, int stringId) {
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
    }
}
