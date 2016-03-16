package ydrasaal.alligregator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Léo on 13/03/2016.
 */
public class SharedPrefUtils {

    static final String SHARED_PREF_FILE = "spfile";
    static final String SHARED_PREF_URLS = "spurls";

    /**
     * Add a new url to the favorites, creating the entry if it's the first
     *
     * @param str
     * @param context
     */
    public static void saveString(String str, Context context) {
        Log.d("SHARED_PREF", "Saving url " + str);
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        Set<String> savedSet = prefs.getStringSet(SHARED_PREF_URLS, null);
        Set<String> newSet;
        if (savedSet == null) newSet = new HashSet<>();
        else newSet = new HashSet<>(savedSet);

        newSet.add(str);

        SharedPreferences.Editor editor = prefs.edit();
        Log.d("SHARED_PREF", "Saving url set of size " + newSet.size());
        for (String s :
                newSet) {
            Log.d("SHARED_PREF", s);
        }
        editor.putStringSet(SHARED_PREF_URLS, newSet);
        editor.apply();
    }

    public static Set<String> getString(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        return prefs.getStringSet(SHARED_PREF_URLS, null);
    }
}
