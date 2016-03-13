package ydrasaal.alligregator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ydrasaal.alligregator.activities.FeedDetailActivity;
import ydrasaal.alligregator.dummy.DummyContent;

/**
 * A fragment representing a single LoadEntry detail screen.
 * This fragment is either contained in a {@link FeedListActivity}
 * in two-pane mode (on tablets) or a {@link FeedDetailActivity}
 * on handsets.
 */
public class FeedDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_SNIPPET = "argsnip";
    public static final String ARG_URL = "argurl";
    public static final String ARG_TITLE = "argtitle";


    Bundle bundle;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        if (bundle == null || !bundle.containsKey(ARG_TITLE)) return;

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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bundle.getString(ARG_URL)));
                startActivity(browserIntent);
            }
        });

        return rootView;
    }
}
