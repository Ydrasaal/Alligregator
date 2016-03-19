package ydrasaal.alligregator.network.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.network.IAlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.network.retrofit.services.FeedServices;
import ydrasaal.alligregator.utils.OptionsManager;

/**
 * Created by Léo on 12/03/2016.
 *
 * Alligregator API implementation using Retrofit 2
 */
public class AlligregatorRetrofitAPI implements IAlligregatorAPI {
    @Override
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public void cancelCall(final Call call) {
        new OkHttpClient().getDispatcher().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                call.cancel();
            }
        });
    }

    @Override
    public Call<FindResults> searchFeed(String keywords, APICallbackListener<FindResults> listener) {
        Call<FindResults> call = this.buildService(FeedServices.FindFeedService.class, BASE_URL_FIND).load(
                "",
                "1.0",
                keywords);
        call.enqueue(createAPICallback(listener));

        return call;
    }

    @Override
    public Call<LoadResults> loadFeed(String url, APICallbackListener<LoadResults> listener) {
        Call<LoadResults> call = this.buildService(FeedServices.LoadFeedService.class, BASE_URL_LOAD).load(
                "",
                "1.0",
                url,
                OptionsManager.getInstance().getLoadedEntryNumber());
        call.enqueue(createAPICallback(listener));

        return call;
    }

    /**
     * Creates a Retrofit Service from a Service class
     *
     * @param serviceClass Service class defining the API call
     * @param baseURL base URL for API calls
     * @param <S> Data model returned in the callback
     * @return new service instance
     */
    private <S> S buildService(Class<S> serviceClass, String baseURL) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(new OkHttpClient())
                .addConverterFactory(getDateAwareGSONConverter())
                .build();

        return retrofit.create(serviceClass);
    }

    /**
     * Creates an API callback object from the APICallBackListener interface
     *
     * @param listener API response listener
     * @param <S> Data model returned in the callback
     * @return new API callback listener
     */
    private <S> Callback<S> createAPICallback(final APICallbackListener<S> listener) {
        return new Callback<S>() {
            @Override
            public void onResponse(retrofit.Response<S> response, Retrofit retrofit) {
                if (response.isSuccess()) listener.onResponseSuccess(response);
                else listener.onResponseFailure();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("SurfinAPIRetrofit", "Retrofit API call failed : " + t.getMessage());
                listener.onFailure();
            }
        };
    }

    /**
     * Creates a custom converter, allowing Strings to be parsed as Date objects where needed

     * @return new converter instance
     */
    private GsonConverterFactory getDateAwareGSONConverter() {
        Gson gson = new GsonBuilder()
                .setDateFormat(TIMESTAMP_FORMAT)
                .create();

        return GsonConverterFactory.create(gson);
    }
}
