package ydrasaal.alligregator.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import ydrasaal.alligregator.R;

/**
 * Created by lchazal on 07/12/15.
 *
 * Base activity for activities using toolbar. Any child of this should include a @layout/toolbar in it's own layout.
 *
 */
public abstract class AToolbarCompatActivity extends AppCompatActivity {

    private static final String TAG = "TOOLBAR_COMPAT_ACTIVITY";

    protected Toolbar toolbar;
    private TextView    toolbarTitle;

    protected void      setupToolbar(int homeIconId, String title) {
        setupToolbar(homeIconId, title, 0);
    }

    protected void      setupToolbar(int homeIconId, String title, int elevation) {
        setToolbarIcon(homeIconId);
        setToolbarTitle(title);
    }

    private void setToolbarIcon(int homeIconId) {

        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(homeIconId);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.d(TAG, "Error setting home indicator : " + e.getMessage());
        }
    }

    private void setToolbarTitle(String title) {
        if (toolbarTitle == null) return;

        toolbarTitle.setText(title);
    }

    protected abstract void onHomeButtonClicked();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onHomeButtonClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
