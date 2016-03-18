package ydrasaal.alligregator.data;

/**
 * Created by lchazal on 18/03/16.
 */
public class EntryItem {

    private String      feedName;
    private LoadEntry   entry;

    public EntryItem(String feedName, LoadEntry entry) {
        this.feedName = feedName;
        this.entry = entry;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public LoadEntry getEntry() {
        return entry;
    }

    public void setEntry(LoadEntry entry) {
        this.entry = entry;
    }
}
