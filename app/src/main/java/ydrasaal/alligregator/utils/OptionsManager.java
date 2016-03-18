package ydrasaal.alligregator.utils;

import android.content.Context;

/**
 * Created by lchazal on 18/03/16.
 *
 * Singleton handling user options
 */
public class OptionsManager {

    private static OptionsManager ourInstance = new OptionsManager();

    public static OptionsManager getInstance() {
        return ourInstance;
    }

    private OptionsManager() {
    }

    private OptionData  options = new OptionData();

    public void retrieveOptionsFromSharedPreferences(Context context) {
        SharedPrefUtils.getOptions(context, options);
    }

    public void saveOptionsToSharedPreferences(Context context) {
        SharedPrefUtils.saveOptions(context,
                options.loadedEntryNumber);
    }

    public int getLoadedEntryNumber() {
        return options.loadedEntryNumber;
    }

    public void setLoadedEntryNumber(int loadedEntryNumber) {
        options.loadedEntryNumber = loadedEntryNumber;
    }

    public class OptionData {
        int loadedEntryNumber = 10;
    }
}
