package ydrasaal.alligregator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import ydrasaal.alligregator.fragments.EntryDetailFragment;
import ydrasaal.alligregator.R;
import ydrasaal.alligregator.utils.ShareManager;

public class EntryDetailActivity extends AppCompatActivity {

    private Bundle  arguments;
    private String  url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        arguments = getIntent().getExtras();

        if (savedInstanceState == null) {
            setupDetailFragment();
        }
        setupToolbar();
        setupFAButton();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFAButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String preview = ShareManager.createEntryPreviewString(arguments);
                if (!preview.isEmpty()) {
                    startActivity(ShareManager.createShareIntent(getString(R.string.share_message) + preview));
                }
            }
        });
    }

    private void setupDetailFragment() {
        EntryDetailFragment fragment = new EntryDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.feed_detail_container, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, LobbyActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
