package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.Set;

import retrofit.Response;
import ydrasaal.alligregator.fragments.EntryDetailFragment;
import ydrasaal.alligregator.R;
import ydrasaal.alligregator.adapters.FeedListAdapter;
import ydrasaal.alligregator.adapters.NavigationDrawerAdapter;
import ydrasaal.alligregator.data.EntryItem;
import ydrasaal.alligregator.data.Feed;
import ydrasaal.alligregator.data.LoadEntry;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.data.MenuItem;
import ydrasaal.alligregator.listeners.OnRecyclerItemClickListener;
import ydrasaal.alligregator.network.AlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.utils.DialogUtils;
import ydrasaal.alligregator.utils.AnimationUtils;
import ydrasaal.alligregator.utils.ColorUtils;
import ydrasaal.alligregator.utils.SharedPrefUtils;
import ydrasaal.alligregator.views.RecyclerLayoutManager;

/**
 * Created by Léo on 13/03/2016.
 *
 * Main screen, displaying entries of feeds registered as favorites
 */
public class LobbyActivity extends AToolbarCompatActivity implements OnRecyclerItemClickListener {

    private static final int   POSITION_SETTINGS = 2;
    private static final int   POSITION_ABOUT = 3;
    private static final int   POSITION_FAVS = 5;

    private boolean mTwoPane;

    private RecyclerView drawerRecyclerView;
    private DrawerLayout drawerLayout;
    private FeedListAdapter adapter;
    private NavigationDrawerAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setupToolbar(R.drawable.icon_navigation, getString(R.string.app_name));
        setupDrawerLayout();
        setupDrawerToggle();
        setupFloatingActionButton();
        setupRecyclerDrawer();
        if (findViewById(R.id.feed_detail_container) != null) {
            mTwoPane = true;
        }
        setupRecyclerView();
        ColorUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    protected void onStart() {
        super.onStart();

        retrieveAPIData();
    }

    /**
     * Make an API call for each url in favorites
     */
    private void retrieveAPIData() {
        Set<String> set = SharedPrefUtils.getURLs(this);
        if (set == null || set.isEmpty()) return;
        for (String url :
                set) {
            AlligregatorAPI.getInstance().loadFeed(url, createFeedAPICallback(url));
        }
    }

    private APICallbackListener<LoadResults> createFeedAPICallback(final String url) {
        return new APICallbackListener<LoadResults>() {
            @Override
            public void onResponseSuccess(Response<LoadResults> response) {
                if (response.body() == null || response.body().getResponseData() == null) return;
                try {
                    Feed feed = response.body().getResponseData().getFeed();
                    menuAdapter.addItem(new MenuItem(feed.getTitle(), false, feed.getLink(), url));
                    if (!feed.getEntries().isEmpty()) {
                        for (LoadEntry entry :
                                feed.getEntries()) {
                            if (entry != null)
                                adapter.addItem(new EntryItem(feed.getLink(), entry));
                            else {
                                LoadEntry f = new LoadEntry();
                                adapter.addItem(new EntryItem(feed.getLink(), f));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (NullPointerException e) {
                    Log.e(LobbyActivity.this.getLocalClassName(), e.getMessage());
                }
            }

            @Override
            public void onResponseFailure() {

            }

            @Override
            public void onFailure() {

            }
        };
    }

    @Override
    protected void onHomeButtonClicked() {
        toggleDrawer(true);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) toggleDrawer(false);
        else super.onBackPressed();
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void setupDrawerToggle() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                setProgressiveStatusBarColor(slideOffset);
            }
        });
        ColorUtils.getInstance()
                .setStartingColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        ColorUtils.getInstance()
                .setEndingColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LobbyActivity.this, SearchFeedActivity.class));
            }
        });
    }

    private void setupRecyclerDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerRecyclerView = (RecyclerView) findViewById(R.id.left_drawer);
        LinearLayoutManager mLinearLayoutmanager = new RecyclerLayoutManager(this);
        drawerRecyclerView.setLayoutManager(mLinearLayoutmanager);

        menuAdapter = new NavigationDrawerAdapter(this);
        menuAdapter.addFixedItem(new MenuItem("", false, "", ""), 0);
        menuAdapter.addFixedItem(new MenuItem("More", true, "", ""), 1);
        menuAdapter.addFixedItem(new MenuItem("Settings", R.drawable.ic_settings_white_24dp, false, "", ""), 2);
        menuAdapter.addFixedItem(new MenuItem("About", R.drawable.ic_info_outline_white_24dp, false, "", ""), 3);
        menuAdapter.addFixedItem(new MenuItem("Favorites", true, "", ""), 4);

        drawerRecyclerView.setAdapter(menuAdapter);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feed_list);
        adapter = new FeedListAdapter(createRecyclerListener());
        recyclerView.setAdapter(adapter);
    }

    /**
     * change the status bar's color depending on the drawer state
     * @param progress drawer progress
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setProgressiveStatusBarColor(float progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int current = ColorUtils.getInstance().getColorProgression(progress);
            getWindow().setStatusBarColor(current);
        }
    }

    private void toggleDrawer(boolean open) {
        if (open) drawerLayout.openDrawer(GravityCompat.START);
        else drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Hide entries except those from the selected feed
     *
     * @param view clicked view
     * @param position view position
     */
    @Override
    public void onClick(View view, int position) {
        toggleDrawer(false);
        switch (position) {
            case POSITION_SETTINGS:
                startActivity(new Intent(this, SettingsActivity.class));
                AnimationUtils.animateIn(this);
                break;
            case POSITION_ABOUT:
                startActivity(new Intent(this, AboutActivity.class));
                AnimationUtils.animateIn(this);
                break;
            default:
                adapter.filterByUrl(menuAdapter.getItem(position).getUrl());
                break;
        }
    }

    /**
     * Display the delete dialog for selected feed
     *
     * @param view clicked view
     * @param position view position
     */
    @Override
    public void onLongCLick(View view, final int position) {
        if (position < POSITION_FAVS) return; //Only applies to favorites

        DialogUtils.displayAlertDialog(this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPrefUtils.deleteURL(LobbyActivity.this, menuAdapter.getItem(position).getSavedURL());
                adapter.removeByUrl(menuAdapter.getItem(position).getUrl());
                menuAdapter.removeItem(position);
            }
        });
    }

    /**
     * Create a listener for the entry list's content
     * Display the detail view on click
     *
     * @return new listener instance
     */
    private OnRecyclerItemClickListener createRecyclerListener() {
        return new OnRecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle arguments = new Bundle();
                arguments.putString(EntryDetailFragment.ARG_URL, adapter.getItem(position).getEntry().getLink());
                arguments.putString(EntryDetailFragment.ARG_SNIPPET, adapter.getItem(position).getEntry().getContentSnippet());
                arguments.putString(EntryDetailFragment.ARG_TITLE, adapter.getItem(position).getEntry().getTitle());
                if (mTwoPane) {
                    EntryDetailFragment fragment = new EntryDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.feed_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(LobbyActivity.this, EntryDetailActivity.class);
                    intent.putExtras(arguments);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongCLick(View view, int position) {

            }
        };
    }
}
