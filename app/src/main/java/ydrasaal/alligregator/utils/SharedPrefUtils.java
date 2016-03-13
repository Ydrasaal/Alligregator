package ydrasaal.alligregator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Léo on 13/03/2016.
 */
public class SharedPrefUtils {

    static final String SHARED_PREF_FILE = "spfile";
    static final String SHARED_PREF_URLS = "spurls";

    public static void saveString(String str, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        Set<String> set = prefs.getStringSet(SHARED_PREF_URLS, null);
        if (set == null) set = new HashSet<>();

        set.add(str);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(SHARED_PREF_URLS, set);
        editor.apply();
    }

    public static Set<String> getString(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        return prefs.getStringSet(SHARED_PREF_URLS, null);
    }
}
