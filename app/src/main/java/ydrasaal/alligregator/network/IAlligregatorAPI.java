package ydrasaal.alligregator.network;

import android.content.Context;

import retrofit.Call;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.network.listeners.APICallbackListener;

/**
 * Created by Léo on 12/03/2016.
 */
public interface IAlligregatorAPI {

    String BASE_URL_FIND = "https://ajax.googleapis.com/ajax/services/feed/find";
    String BASE_URL_LOAD = "https://ajax.googleapis.com/ajax/services/feed/load";

    boolean isNetworkAvailable(Context context);
    void    cancelCall(Call call);

    Call<FindResults>   searchFeed(String keywords, APICallbackListener<FindResults> listener);
    Call<LoadResults> loadFeed(String url, APICallbackListener<LoadResults> listener);
}
