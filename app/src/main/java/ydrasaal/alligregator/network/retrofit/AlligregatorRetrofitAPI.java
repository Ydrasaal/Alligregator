package ydrasaal.alligregator.network.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.network.IAlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.network.retrofit.services.FeedServices;
import ydrasaal.alligregator.utils.DateConverter;
import ydrasaal.alligregator.utils.OptionsManager;

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
                OptionsManager.getInstance().getLoadedEntryNumber());
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
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(getExclusiveGSONConverter())
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

    private GsonConverterFactory getExclusiveGSONConverter() {
//        Type token = new TypeToken<RealmList<RealmInt>>() {
//        }.getType();
        Gson gson = new GsonBuilder()
//                .setExclusionStrategies(new ExclusionStrategy() { // Dodge GSON's bug causing stack overflow on RealmObject parsing
//                    @Override
//                    public boolean shouldSkipField(FieldAttributes f) {
//                        return f.getDeclaringClass().equals(RealmObject.class);
//                    }
//
//                    @Override
//                    public boolean shouldSkipClass(Class<?> clazz) {
//                        return false;
//                    }
//                })
//                .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmInt>>() { // Allow parsing of RealmInt inside RealmLists without crash
//
//                    @Override
//                    public void write(JsonWriter out, RealmList<RealmInt> value) throws IOException {
//                        // Ignore
//                    }
//
//                    @Override
//                    public RealmList<RealmInt> read(JsonReader in) throws IOException {
//                        RealmList<RealmInt> list = new RealmList<RealmInt>();
//                        in.beginArray();
//                        while (in.hasNext()) {
//                            list.add(new RealmInt(in.nextInt()));
//                        }
//                        in.endArray();
//                        return list;
//                    }
//                })
                .setDateFormat(DateConverter.TIMESTAMP_FORMAT) // Allow timestamps to be converted to Date object directly when necessary
                .create();

        return GsonConverterFactory.create(gson);
    }
}
