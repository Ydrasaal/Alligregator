package ydrasaal.alligregator.utils;

import android.content.Intent;
import android.os.Bundle;

import ydrasaal.alligregator.FeedDetailFragment;

/**
 * Created by Léo on 16/03/2016.
 *
 * A helper creating share intents used by the app to send URLs
 */
public class ShareManager {

    public static Intent createShareIntent(String message) {
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");
        return intent;
    }

    public static String createEntryPreviewString(Bundle arguments) {
        String preview = "";

        if (arguments != null) {
            if (arguments.get(FeedDetailFragment.ARG_TITLE) != null)
                preview += arguments.getString(FeedDetailFragment.ARG_TITLE) + "\n\n";
            if (arguments.get(FeedDetailFragment.ARG_SNIPPET) != null)
                preview += arguments.getString(FeedDetailFragment.ARG_SNIPPET) + "\n\n";
            if (arguments.get(FeedDetailFragment.ARG_URL) != null)
                preview += arguments.getString(FeedDetailFragment.ARG_URL);
        }

        return preview;
    }
}
