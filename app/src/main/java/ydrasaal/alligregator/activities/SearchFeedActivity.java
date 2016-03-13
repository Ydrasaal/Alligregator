package ydrasaal.alligregator.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;
import ydrasaal.alligregator.R;
import ydrasaal.alligregator.data.FindEntry;
import ydrasaal.alligregator.data.LoadEntry;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.network.AlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.utils.SharedPrefUtils;

/**
 * Created by Léo on 13/03/2016.
 */
public class SearchFeedActivity extends AToolbarCompatActivity {

    private Call ongoingCall;

    private boolean mTwoPane;

    private List<FindEntry>          results;
    private SearchFeedAdapter   adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_feed);

        setupToolbar(R.drawable.ic_navigation_back, getString(R.string.title_search_feed));
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        results = new ArrayList<>();
        if (findViewById(R.id.feed_detail_container) != null) {
            mTwoPane = true;
        }

        setupSearchView();
        setupResultsView();
    }

    @Override
    public void onBackPressed() {
        onHomeButtonClicked();
    }

    @Override
    protected void onHomeButtonClicked() {
        if (ongoingCall != null) AlligregatorAPI.getInstance().cancelCall(ongoingCall);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    private void setupSearchView() {
        SearchView view = (SearchView) findViewById(R.id.search_view);
        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFeeds(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFeeds(newText);
                return true;
            }
        });
    }

    private void setupResultsView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feed_list);
        adapter = new SearchFeedAdapter(results);
        recyclerView.setAdapter(adapter);
    }

    private void searchFeeds(String params) {
        if (params.length() < 3) return;
        if (ongoingCall != null) AlligregatorAPI.getInstance().cancelCall(ongoingCall);
        ongoingCall = AlligregatorAPI.getInstance().searchFeed(params, createSearchCallback());
    }

    private APICallbackListener<FindResults>   createSearchCallback() {
        return new APICallbackListener<FindResults>() {
            @Override
            public void onResponseSuccess(Response<FindResults> response) {
                Log.d("LOG", "We got a response");
                if (response.body() == null) {
                    Log.d("LOG", "response null");
                    return;
                }
                if (response.body().getResponseData() == null) {
                    Log.d("LOG", "query null");
                    return;
                }
                if (response.body().getResponseData().getEntries() == null) {
                    Log.d("LOG", "entries null");
                    return;
                }
                if (response.body().getResponseData().getEntries().isEmpty()) {
                    Log.d("LOG", "entries empty");
                } else {
                    Log.d("LOG", "entries : " + response.body().getResponseData().getEntries().size());
                    results.clear();
                    for (FindEntry entry :
                            response.body().getResponseData().getEntries()) {
                        if (entry != null) results.add(entry);
                        else {
                            FindEntry f = new FindEntry();
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
        };
    }

    public class SearchFeedAdapter
            extends RecyclerView.Adapter<SearchFeedAdapter.ViewHolder> {

        private final List<FindEntry> mValues;

        public SearchFeedAdapter(List<FindEntry> items) {
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

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO LOGIC
                    SharedPrefUtils.saveString(holder.mItem.getUrl(), SearchFeedActivity.this);
                    Log.d("LOG", "Added " + holder.mItem.getUrl());
                }
            });
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mTwoPane) {
//                        Bundle arguments = new Bundle();
////                        arguments.putString(FeedContentFragment.ARG_FEED_URL, "osef");
//                        FeedContentFragment fragment = new FeedContentFragment();
//                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.feed_detail_container, fragment)
//                                .commit();
//                    } else {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, FeedContentActivity.class);
////                        intent.putExtra(FeedContentFragment.ARG_FEED_URL, "osef");
//
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView title;
            public ImageButton  button;
            public FindEntry mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                title = (TextView) view.findViewById(R.id.title);
                button = (ImageButton) view.findViewById(R.id.button);
            }
        }
    }
}
