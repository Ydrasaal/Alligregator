package ydrasaal.alligregator.network.retrofit.services;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Url;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.data.LoadResults;

/**
 * Created by Léo on 12/03/2016.
 */
public class FeedServices {

    public interface FindFeedService {
        @GET
        Call<FindResults> load(
                @Url String emptyUrl,
                @Query("v") String version,
                @Query("q") String url);
    }

    public interface LoadFeedService {
        @GET
        Call<LoadResults> load(
                @Url String emptyUrl,
                @Query("v") String version,
                @Query("q") String keywords,
                @Query("num") int resultNumber);
    }
}
