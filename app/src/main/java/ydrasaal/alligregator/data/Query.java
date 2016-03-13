package ydrasaal.alligregator.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Léo on 13/03/2016.
 */
public class Query {

    @SerializedName("entries")
    private List<LoadEntry> entries;

    public List<LoadEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LoadEntry> entries) {
        this.entries = entries;
    }
}
