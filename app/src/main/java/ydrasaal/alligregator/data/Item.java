package ydrasaal.alligregator.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Léo on 12/03/2016.
 */
public class Item {

    @SerializedName("entries")
    private List<LoadEntry> entries;

    public List<LoadEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LoadEntry> entries) {
        this.entries = entries;
    }
}
