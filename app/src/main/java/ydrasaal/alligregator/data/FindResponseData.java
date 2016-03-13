
package ydrasaal.alligregator.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FindResponseData {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("entries")
    @Expose
    private List<FindEntry> entries = new ArrayList<>();

    /**
     * 
     * @return
     *     The query
     */
    public String getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 
     * @return
     *     The entries
     */
    public List<FindEntry> getEntries() {
        return entries;
    }

    /**
     * 
     * @param entries
     *     The entries
     */
    public void setEntries(List<FindEntry> entries) {
        this.entries = entries;
    }

}
