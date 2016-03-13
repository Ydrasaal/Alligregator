package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit.Response;
import ydrasaal.alligregator.FeedDetailFragment;
import ydrasaal.alligregator.R;
import ydrasaal.alligregator.adapters.NavigationDrawerAdapter;
import ydrasaal.alligregator.data.LoadEntry;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.data.MenuItem;
import ydrasaal.alligregator.network.AlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.utils.ColorUtils;
import ydrasaal.alligregator.utils.SharedPrefUtils;

/**
 * Created by Léo on 13/03/2016.
 */
public class LobbyActivity extends AToolbarCompatActivity implements NavigationDrawerAdapter.OnClickListener {

    private boolean mTwoPane;

    private RecyclerView drawerRecyclerView;
    private DrawerLayout drawerLayout;

    private List<LoadEntry> results;
    private FeedListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setupToolbar(R.drawable.icon_navigation, "Alligregator");
        setupDrawerLayout();
        setupDrawerToggle();
        setupFloatingActionButton();
        setupRecyclerDrawer();
        results = new ArrayList<>();
        if (findViewById(R.id.feed_detail_container) != null) {
            mTwoPane = true;
        }
        setupRecyclerView();
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Set<String> set = SharedPrefUtils.getString(this);
        if (set != null && !set.isEmpty()) {
            Log.d("LOG", "We got favorites !");
            Iterator<String> i = set.iterator();

            String url = i.next();
            AlligregatorAPI.getInstance().loadFeed(url, new APICallbackListener<LoadResults>() {
                @Override
                public void onResponseSuccess(Response<LoadResults> response) {
                    Log.d("LOG", "We got a response");
                    if (response.body() == null) {
                        Log.d("LOG", "response null");
                        return;
                    }
                    if (response.body().getResponseData() == null) {
                        Log.d("LOG", "query null");
                        return;
                    }
                    if (response.body().getResponseData().getFeed() == null) {
                        Log.d("LOG", "feed null");
                        return;
                    }
                    if (response.body().getResponseData().getFeed().getEntries().isEmpty()) {
                        Log.d("LOG", "entries empty");
                    } else {
                        Log.d("LOG", "entries : " + response.body().getResponseData().getFeed().getEntries().size());
                        results.clear();
                        for (LoadEntry entry :
                                response.body().getResponseData().getFeed().getEntries()) {
                            if (entry != null) results.add(entry);
                            else {
                                LoadEntry f = new LoadEntry();
                                f.setTitle("LoadEntry a mano");
                                results.add(f);
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onResponseFailure() {

                }

                @Override
                public void onFailure() {

                }
            });
            return;
        }

        AlligregatorAPI.getInstance().loadFeed("http://feeds.gawker.com/kotaku/full", new APICallbackListener<LoadResults>() {
            @Override
            public void onResponseSuccess(Response<LoadResults> response) {
                Log.d("LOG", "We got a response");
                if (response.body() == null) {
                    Log.d("LOG", "response null");
                    return;
                }
                if (response.body().getResponseData() == null) {
                    Log.d("LOG", "query null");
                    return;
                }
                if (response.body().getResponseData().getFeed() == null) {
                    Log.d("LOG", "feed null");
                    return;
                }
                if (response.body().getResponseData().getFeed().getEntries().isEmpty()) {
                    Log.d("LOG", "entries empty");
                } else {
                    Log.d("LOG", "entries : " + response.body().getResponseData().getFeed().getEntries().size());
                    results.clear();
                    for (LoadEntry entry :
                            response.body().getResponseData().getFeed().getEntries()) {
                        if (entry != null) results.add(entry);
                        else {
                            LoadEntry f = new LoadEntry();
                            f.setTitle("LoadEntry a mano");
                            results.add(f);
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onResponseFailure() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void onHomeButtonClicked() {
        toogleDrawer(true);
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
        LinearLayoutManager mLinearLayoutmanager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(mLinearLayoutmanager);

        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem("azerty", true));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", true));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        list.add(new MenuItem("azerty", false));
        drawerRecyclerView.setAdapter(new NavigationDrawerAdapter(list, this));
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feed_list);
        adapter = new FeedListAdapter(results);
        recyclerView.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setProgressiveStatusBarColor(float progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int current = ColorUtils.getInstance().getColorProgression(progress);
            getWindow().setStatusBarColor(current);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    private void toogleDrawer(boolean open) {
        if (open) drawerLayout.openDrawer(GravityCompat.START);
        else drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("LOG", "Clicked position " + position);
    }

    public class FeedListAdapter
            extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

        private final List<LoadEntry> mValues;

        public FeedListAdapter(List<LoadEntry> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_search_result, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.title.setText(Html.fromHtml(mValues.get(position).getTitle()));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(FeedDetailFragment.ARG_URL, holder.mItem.getLink());
                        arguments.putString(FeedDetailFragment.ARG_SNIPPET, holder.mItem.getContentSnippet());
                        arguments.putString(FeedDetailFragment.ARG_TITLE, holder.mItem.getTitle());
                        FeedDetailFragment fragment = new FeedDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.feed_detail_container, fragment)
                                .commit();
                    } else {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.getLink()));
                        startActivity(browserIntent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView title;
            public LoadEntry mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                title = (TextView) view.findViewById(R.id.title);
            }
        }
    }
}
