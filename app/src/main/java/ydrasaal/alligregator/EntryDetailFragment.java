package ydrasaal.alligregator;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import ydrasaal.alligregator.activities.EntryDetailActivity;
import ydrasaal.alligregator.utils.ChromeUtils;

/**
 * A fragment representing a single LoadEntry detail screen.
 * This fragment is either contained in a {@link FeedListActivity}
 * in two-pane mode (on tablets) or a {@link EntryDetailActivity}
 * on handsets.
 */
public class EntryDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_SNIPPET = "argsnip";
    public static final String ARG_URL = "argurl";
    public static final String ARG_TITLE = "argtitle";

    private String contentURL;

    Bundle bundle;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        if (bundle == null || !bundle.containsKey(ARG_URL)) {
            Log.d("DetailFragment", "No bundle/url ...");
            return;
        }

        contentURL = bundle.getString(ARG_URL);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(bundle.getString(ARG_TITLE));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed_detail, container, false);

        ((TextView) rootView.findViewById(R.id.feed_detail)).setText(bundle.getString(ARG_SNIPPET));



        rootView.findViewById(R.id.detail_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChromeUtils.openUrlInCustomTab(getActivity(), Uri.parse(contentURL));
            }
        });

        return rootView;
    }
}
