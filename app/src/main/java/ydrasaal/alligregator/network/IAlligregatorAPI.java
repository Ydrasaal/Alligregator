package ydrasaal.alligregator.network;

import android.content.Context;

import retrofit.Call;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.network.listeners.APICallbackListener;

/**
 * Created by Léo on 12/03/2016.
 *
 * API calls definition
 */
public interface IAlligregatorAPI {

    String BASE_URL = "http://scumbag-debian.fr:9234";

    String BASE_URL_FIND = "https://ajax.googleapis.com/ajax/services/feed/find";
    String BASE_URL_LOAD = "https://ajax.googleapis.com/ajax/services/feed/load";
    String TIMESTAMP_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

    boolean isNetworkAvailable(Context context);
    void    cancelCall(Call call);

    Call<FindResults>   searchFeed(String keywords, APICallbackListener<FindResults> listener);
    Call<LoadResults> loadFeed(String url, APICallbackListener<LoadResults> listener);

//    Call<Void>          createUser(APICallbackListener<Void> listener, User user);
//    Call<Void>          deleteUser(APICallbackListener<Void> listener);
//    Call<Void>          updateUser(APICallbackListener<Void> listener, User user);
//
//    Call<Void>          subscribeToRSS(APICallbackListener<Void> listener, String url);
//    Call<Void>          unsubscribeToRSS(APICallbackListener<Void> listener, String url);
//
//    Call<Void>          readArticle(APICallbackListener<Void> listener, int id);
//    Call<Void>          unreadArticle(APICallbackListener<Void> listener, int id);
//
//    Call<Void>          getAllStatus(APICallbackListener<Void> listener);
//    Call<Void>          getAllArticle(APICallbackListener<Void> listener);
//    Call<Void>          getAllStatusOfFeed(APICallbackListener<Void> listener, int id);
//    Call<Void>          getAllArticleOfFeed(APICallbackListener<Void> listener, int id);
//    Call<Void>          getStatusOfArticle(APICallbackListener<Void> listener, int id);
//    Call<Void>          getArticle(APICallbackListener<Void> listener, int id);
//    Call<Void>          getSubscriptions(APICallbackListener<Void> listener);
}
