package ydrasaal.alligregator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Léo on 13/03/2016.
 */
public class SharedPrefUtils {

    static final String SHARED_PREF_FILE = "spfile";
    static final String SHARED_PREF_URLS = "spurls";
    static final String SHARED_PREF_SIZE_OPTION = "spsizeopt";


    /**
     * Add a new url to the favorites, creating the entry if it's the first
     *
     * @param context
     * @param str
     */
    public static void saveURL(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        Set<String> savedSet = prefs.getStringSet(SHARED_PREF_URLS, null);
        Set<String> newSet;
        if (savedSet == null) newSet = new HashSet<>();
        else newSet = new HashSet<>(savedSet);

        newSet.add(str);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(SHARED_PREF_URLS, newSet);
        editor.apply();
    }

    public static Set<String> getURLs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        return prefs.getStringSet(SHARED_PREF_URLS, null);
    }

    public static void deleteURL(Context context, String url) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        Set<String> savedSet = prefs.getStringSet(SHARED_PREF_URLS, null);
        Set<String> newSet;
        if (savedSet == null) newSet = new HashSet<>();
        else newSet = new HashSet<>(savedSet);

        newSet.remove(url);

        Log.d("SPREF", "removed : " + url);
        for (String s :
                newSet) {
            Log.d("SPREF", s);
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(SHARED_PREF_URLS, newSet);
        editor.apply();
    }

    public static void saveOptions(Context context, int sizeValue) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(SHARED_PREF_SIZE_OPTION, sizeValue);
        editor.apply();
    }

    public static void getOptions(Context context, @NonNull OptionsManager.OptionData optionData) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);

        optionData.loadedEntryNumber = prefs.getInt(SHARED_PREF_SIZE_OPTION, 10);
    }
}
