package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
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
import ydrasaal.alligregator.fragment_entry_detail.EntryDetailFragment;
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
import ydrasaal.alligregator.utils.AnimationUtils;
import ydrasaal.alligregator.utils.ColorUtils;
import ydrasaal.alligregator.utils.SharedPrefUtils;
import ydrasaal.alligregator.views.RecyclerLayoutManager;

/**
 * Created by Léo on 13/03/2016.
 */
public class LobbyActivity extends AToolbarCompatActivity implements OnRecyclerItemClickListener {

    private final int   POSITION_SETTINGS = 2;
    private final int   POSITION_ABOUT = 3;
    private final int   POSITION_FAVS = 5;

    private boolean mTwoPane;

    private RecyclerView drawerRecyclerView;
    private DrawerLayout drawerLayout;

    private FeedListAdapter adapter;

    private NavigationDrawerAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setupToolbar(R.drawable.icon_navigation, "Alligregator");
        setupDrawerLayout();
        setupDrawerToggle();
        setupFloatingActionButton();
        setupRecyclerDrawer();
//        results = new ArrayList<>();
        if (findViewById(R.id.feed_detail_container) != null) {
            mTwoPane = true;
        }
        setupRecyclerView();
        ColorUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Set<String> set = SharedPrefUtils.getURLs(this);
        if (set != null && !set.isEmpty()) {
            Log.d("LOG", "We got favorites !");




//            Iterator<String> i = set.iterator();
//
//            String url = i.next();
            for (String url :
                    set) {
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
                    Feed feed = response.body().getResponseData().getFeed();

                    menuAdapter.addItem(new MenuItem(feed.getTitle(), false, feed.getLink()));
                    if (feed.getEntries().isEmpty()) {
                        Log.d("LOG", "entries empty");
                    } else {
                        Log.d("LOG", "entries : " + feed.getEntries().size());
//                        results.clear();
                        for (LoadEntry entry :
                                feed.getEntries()) {
                            if (entry != null) adapter.addItem(new EntryItem(feed.getLink(), entry));
                            else {
                                LoadEntry f = new LoadEntry();
                                f.setTitle("LoadEntry a mano");
                                adapter.addItem(new EntryItem(feed.getLink(), f));
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

        }
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
        menuAdapter.addFixedItem(new MenuItem("", false, ""), 0);
        menuAdapter.addFixedItem(new MenuItem("More", true, ""), 1);
        menuAdapter.addFixedItem(new MenuItem("Settings", R.drawable.ic_share_white_24dp, false, ""), 2);
        menuAdapter.addFixedItem(new MenuItem("About", R.drawable.ic_share_white_24dp, false, ""), 3);
        menuAdapter.addFixedItem(new MenuItem("Favorites", true, ""), 4);

        drawerRecyclerView.setAdapter(menuAdapter);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feed_list);
        adapter = new FeedListAdapter(createRecyclerListener());
        recyclerView.setAdapter(adapter);
    }

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

    @Override
    public void onClick(View view, int position) {
        Log.d("LOG", "Clicked position " + position);
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
                adapter.removeAllByUrl(menuAdapter.getItem(position).getUrl());
                break;

        }
    }

    @Override
    public void onLongCLick(View view, int position) {
        Log.d("LOG", "Long Clicked position " + position);
        if (position <= POSITION_FAVS) return;

        //TODO delete activity
    }

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
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.getLink()));
//                        startActivity(browserIntent);
                }
            }

            @Override
            public void onLongCLick(View view, int position) {

            }
        };
    }

//    public class FeedListAdapter
//            extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {
//
//        private final List<LoadEntry> mValues;
//
//        public FeedListAdapter(List<LoadEntry> items) {
//            mValues = items;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.viewholder_entry, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mItem = mValues.get(position);
//            holder.title.setText(Html.fromHtml(mValues.get(position).getTitle()));
//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(EntryDetailFragment.ARG_URL, holder.mItem.getLink());
//                    arguments.putString(EntryDetailFragment.ARG_SNIPPET, holder.mItem.getContentSnippet());
//                    arguments.putString(EntryDetailFragment.ARG_TITLE, holder.mItem.getTitle());
//                    if (mTwoPane) {
//                        EntryDetailFragment fragment = new EntryDetailFragment();
//                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.feed_detail_container, fragment)
//                                .commit();
//                    } else {
//                        Intent intent = new Intent(LobbyActivity.this, EntryDetailActivity.class);
//                        intent.putExtras(arguments);
//                        startActivity(intent);
////                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.getLink()));
////                        startActivity(browserIntent);
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public final View mView;
//            public final TextView title;
//            public LoadEntry mItem;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                title = (TextView) view.findViewById(R.id.title);
//            }
//        }
//    }
}
