package ydrasaal.alligregator.network.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.network.IAlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.network.retrofit.services.FeedServices;

/**
 * Created by Léo on 12/03/2016.
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
                20);
        call.enqueue(createAPICallback(listener));

        return call;
    }

    private <S> S buildService(Class<S> serviceClass, String baseURL) {

        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
//        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(getExclusiveGSONConverter())
                .build();
        S service = retrofit.create(serviceClass);

        return service;
    }

    private <S> Callback<S> createAPICallback(final APICallbackListener<S> listener) {
        Callback<S> callback = new Callback<S>() {
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

        return callback;
    }
}
