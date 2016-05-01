package ydrasaal.alligregator.realm;

import android.app.Application;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by lchazal on 29/09/15.
 *
 */
public class AlligregatorRealm {

    private static AlligregatorRealm mInstance;
    private static RealmConfiguration   mConfiguration;
    private static Realm                mRealmInstance;

    public static void initialize(Application application) {
        mConfiguration = new RealmConfiguration.Builder(application).build();
        getInstance();
    }

    public static AlligregatorRealm getInstance() {
        if (mInstance == null) {
            mInstance = new AlligregatorRealm();
        }
        if (mRealmInstance == null) mRealmInstance = Realm.getDefaultInstance();
        return mInstance;
    }

    private AlligregatorRealm() {
        Realm.setDefaultConfiguration(mConfiguration);
    }

    public void deleteRealm() {
        if (!mRealmInstance.isClosed()) mRealmInstance.close();
        mRealmInstance = null;
        Realm.deleteRealm(mConfiguration);
    }

    public void addOrUpdateObject(RealmObject object) {
        mRealmInstance.beginTransaction();
        mRealmInstance.copyToRealmOrUpdate(object);
        mRealmInstance.commitTransaction();
    }

    public void addOrUpdateObjectList(List<? extends RealmObject> list) {
        mRealmInstance.beginTransaction();
        mRealmInstance.copyToRealmOrUpdate(list);
        mRealmInstance.commitTransaction();
    }
}
