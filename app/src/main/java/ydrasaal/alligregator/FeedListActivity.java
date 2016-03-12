package ydrasaal.alligregator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import ydrasaal.alligregator.adapters.NavigationDrawerAdapter;
import ydrasaal.alligregator.data.MenuItem;
import ydrasaal.alligregator.dummy.DummyContent;
import ydrasaal.alligregator.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Feeds. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FeedDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FeedListActivity extends AppCompatActivity implements NavigationDrawerAdapter.OnClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private RecyclerView drawerRecyclerView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.feed_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        if (findViewById(R.id.feed_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerRecyclerView = (RecyclerView) findViewById(R.id.left_drawer);
        LinearLayoutManager mLinearLayoutmanager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(mLinearLayoutmanager);
        // improve performance by indicating the list if fixed size.
        drawerRecyclerView.setHasFixedSize(true);

        // set up the drawer's list view with items and click listener
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

        ColorUtils.getInstance().setStartingColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ColorUtils.getInstance().setEndingColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        android.support.v7.app.ActionBarDrawerToggle mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.title_feed_detail,
                R.string.title_feed_detail
        ) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                setProgressiveStatusBarColor(slideOffset);
            }
        };

        drawerLayout.addDrawerListener(mDrawerToggle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setProgressiveStatusBarColor(float progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int current = ColorUtils.getInstance().getColorProgression(progress);
            getWindow().setStatusBarColor(current);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("LOG", "Clicked position " + position);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(FeedDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        FeedDetailFragment fragment = new FeedDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.feed_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, FeedDetailActivity.class);
                        intent.putExtra(FeedDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
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
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
