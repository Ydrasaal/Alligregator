package ydrasaal.alligregator.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by lchazal on 18/03/16.
 *
 * Service used to keep the application alive while in the background, so Chrome Tabs doesn't get it killed
 */
public class WakeService extends Service {

    private static final Binder sBinder = new Binder();

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }
}