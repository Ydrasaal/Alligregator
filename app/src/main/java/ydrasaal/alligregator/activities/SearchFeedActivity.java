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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;
import ydrasaal.alligregator.R;
import ydrasaal.alligregator.data.FindEntry;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.network.AlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.utils.AnimationUtils;
import ydrasaal.alligregator.utils.ColorUtils;
import ydrasaal.alligregator.utils.SharedPrefUtils;
import ydrasaal.alligregator.utils.ToastUtils;

/**
 * Created by Léo on 13/03/2016.
 *
 * Activity allowing the user to find feeds from keywords
 */
public class SearchFeedActivity extends AToolbarCompatActivity {

    private Call ongoingCall;

    private List<FindEntry>          results;
    private SearchFeedAdapter   adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_feed);

        setupToolbar(R.drawable.ic_navigation_back, getString(R.string.title_search_feed));

        results = new ArrayList<>();

        setupSearchView();
        setupResultsView();
        ColorUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void onBackPressed() {
        onHomeButtonClicked();
    }

    /**
     * Before finishing this activity, we make sure any ongoing Call is canceled so it doesn't return to a destroyed Activity
     */
    @Override
    protected void onHomeButtonClicked() {
        if (ongoingCall != null) AlligregatorAPI.getInstance().cancelCall(ongoingCall);
        AnimationUtils.animateOut(this);
        finish();
    }

    /**
     * Add a callback to the SearchView, firing off API data retrieval for the entered keyword(s)
     */
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

    /**
     * Call API with the entered String (3 characters minimum), cancelling any ongoing call
     *
     * @param params user input
     */
    private void searchFeeds(String params) {
        if (params.length() < 3) return;
        if (ongoingCall != null) AlligregatorAPI.getInstance().cancelCall(ongoingCall);
        ongoingCall = AlligregatorAPI.getInstance().searchFeed(params, createSearchCallback());
    }

    /**
     * create a listener for the Search service
     *
     * @return new listener instance
     */
    private APICallbackListener<FindResults>   createSearchCallback() {
        return new APICallbackListener<FindResults>() {
            @Override
            public void onResponseSuccess(Response<FindResults> response) {
                try {
                    if (response.body().getResponseData().getEntries().isEmpty()) return;
                    results.clear();
                    for (FindEntry entry :
                            response.body().getResponseData().getEntries()) {
                        if (entry != null) results.add(entry);
                        else {
                            FindEntry f = new FindEntry();
                            results.add(f);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                    Log.e(SearchFeedActivity.this.getLocalClassName(), e.getMessage());
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

    /**
     * Simple adapter displaying the results as list
     */
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
                    SharedPrefUtils.saveURL(SearchFeedActivity.this, holder.mItem.getUrl());
                    ToastUtils.displayToast(SearchFeedActivity.this, R.string.add_toast);
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
            public ImageButton  button;
            public FindEntry mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                title = (TextView) view.findViewById(R.id.entry_title);
                button = (ImageButton) view.findViewById(R.id.button);
            }
        }
    }
}
