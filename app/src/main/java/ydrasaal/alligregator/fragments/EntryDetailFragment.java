package ydrasaal.alligregator.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.utils.ChromeUtils;
import ydrasaal.alligregator.utils.ShareManager;

/**
 * EntryDetailActivity's nested content, display fullscreen on smartphones and in a side view on tablets.
 */
public class EntryDetailFragment extends Fragment {

    public static final String ARG_SNIPPET = "argsnip";
    public static final String ARG_URL = "argurl";
    public static final String ARG_TITLE = "argtitle";

    private String contentURL;

    Bundle bundle;

    public EntryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        if (bundle == null || !bundle.containsKey(ARG_URL)) {
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

        String snippet = bundle.getString(ARG_SNIPPET, "");

        if (!snippet.isEmpty()) {
            ((TextView) rootView.findViewById(R.id.feed_detail)).setText(bundle.getString(ARG_SNIPPET));
        }

        rootView.findViewById(R.id.detail_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChromeUtils.openUrlInCustomTab(getActivity(), Uri.parse(contentURL));
            }
        });
        setupFAButton(rootView);

        return rootView;
    }

    /**
     * Bind the FloatingActionButton to start sharing intents
     *
     * @param rootView button's view
     */
    private void setupFAButton(View rootView) {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        if (fab == null) return;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String preview = ShareManager.createEntryPreviewString(bundle);
                if (!preview.isEmpty()) {
                    startActivity(ShareManager.createShareIntent(getString(R.string.share_message) + preview));
                }
            }
        });
    }
}
