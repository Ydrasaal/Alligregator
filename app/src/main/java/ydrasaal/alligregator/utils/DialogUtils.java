package ydrasaal.alligregator.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import ydrasaal.alligregator.R;

/**
 * Created by Léo on 19/03/2016.
 *
 * Helper class creating and display AlertDialogs from Activity context
 */
public class DialogUtils {

    public static void displayAlertDialog(Context context, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        dialogBuilder.setTitle(R.string.dialog_title);
        dialogBuilder.setMessage(R.string.dialog_message)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_ok, listener)
                .setNegativeButton(R.string.dialog_ko, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
