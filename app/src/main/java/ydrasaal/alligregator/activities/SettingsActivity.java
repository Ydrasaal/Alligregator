package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.utils.OptionsManager;

/**
 * Created by lchazal on 18/03/16.
 *
 * Settings screen
 */
public class SettingsActivity extends AToolbarCompatActivity {

    private TextView    entryDisplay;

    private int entryNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        retrieveSavedOptions();
        Log.d("SETTINGS", "Saved settings :");
        Log.d("SETTINGS", "Entry number = " + entryNumber);

        setupToolbar(R.drawable.ic_navigation_back, getString(R.string.settings_toolbar));
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        setupEntrySeekbar();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    private void retrieveSavedOptions() {
        OptionsManager manager = OptionsManager.getInstance();
        manager.retrieveOptionsFromSharedPreferences(this);
        entryNumber = manager.getLoadedEntryNumber();
    }

    private void updateEntryDisplay() {
        entryDisplay.setText(String.format(getString(R.string.settings_number), entryNumber));
    }

    private void setupEntrySeekbar() {
        entryDisplay = (TextView) findViewById(R.id.entry_number_title);
        SeekBar entrySeekbar = (SeekBar) findViewById(R.id.entry_number_seekbar);
        entrySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                entryNumber = progress / 2;
                if (entryNumber == 0) entryNumber = 1;
                updateEntryDisplay();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //DO NOTHING
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //DO NOTHING
            }
        });
        entrySeekbar.setProgress(entryNumber == 1 ? entryNumber : entryNumber * 2);
        updateEntryDisplay();
    }

    @Override
    public void onBackPressed() {
        OptionsManager manager = OptionsManager.getInstance();
        manager.setLoadedEntryNumber(entryNumber);
        manager.saveOptionsToSharedPreferences(this);

        Log.d("SETTINGS", "Saving settings :");
        Log.d("SETTINGS", "Entry number = " + entryNumber);


        finish();
    }

    @Override
    protected void onHomeButtonClicked() {
        onBackPressed();
    }
}
