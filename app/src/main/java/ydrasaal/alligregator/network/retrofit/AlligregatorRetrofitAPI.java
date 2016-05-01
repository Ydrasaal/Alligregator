package ydrasaal.alligregator.network.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import ydrasaal.alligregator.data.FindResults;
import ydrasaal.alligregator.data.LoadResults;
import ydrasaal.alligregator.network.IAlligregatorAPI;
import ydrasaal.alligregator.network.listeners.APICallbackListener;
import ydrasaal.alligregator.network.retrofit.services.FeedServices;
import ydrasaal.alligregator.network.retrofit.services.Services;
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

//    @Override
//    public Call<Void> createUser(APICallbackListener<Void> listener, User user) {
//        Call<Void> call = this.buildService(Services.CreateUserService.class, BASE_URL).load(
//                user);
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> deleteUser(APICallbackListener<Void> listener) {
//        Call<Void> call = this.buildService(Services.DeleteUserService.class, BASE_URL).load(
//            getAuthString()
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> updateUser(APICallbackListener<Void> listener, User user) {
//        Call<Void> call = this.buildService(Services.UpdateUserService.class, BASE_URL).load(
//                getAuthString(),
//                user);
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> subscribeToRSS(APICallbackListener<Void> listener, String url) {
//        String url64;
//        try {
//            url64 = getbase64String(url);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            url64 = "encode failure";
//        }
//
//        Call<Void> call = this.buildService(Services.SubscribeRSSService.class, BASE_URL).load(
//                getAuthString(),
//                url64
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> unsubscribeToRSS(APICallbackListener<Void> listener, String url) {
//        Call<Void> call = this.buildService(Services.UnubscribeRSSService.class, BASE_URL).load(
//                getAuthString(),
//                url
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;    }
//
//    @Override
//    public Call<Void> readArticle(APICallbackListener<Void> listener, int id) {
//        Call<Void> call = this.buildService(Services.ReadArticleService.class, BASE_URL).load(
//                getAuthString(),
//                id
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> unreadArticle(APICallbackListener<Void> listener, int id) {
//        Call<Void> call = this.buildService(Services.UneadArticleService.class, BASE_URL).load(
//                getAuthString(),
//                id
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> getAllStatus(APICallbackListener<Void> listener) {
//        Call<Void> call = this.buildService(Services.AllStatusService.class, BASE_URL).load(
//                getAuthString()
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> getAllArticle(APICallbackListener<Void> listener) {
//        Call<Void> call = this.buildService(Services.AllArticleService.class, BASE_URL).load(
//                getAuthString()
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> getAllStatusOfFeed(APICallbackListener<Void> listener, int id) {
//        Call<Void> call = this.buildService(Services.AllStatusFeedService.class, BASE_URL).load(
//                getAuthString(),
//                id
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> getAllArticleOfFeed(APICallbackListener<Void> listener, int id) {
//        Call<Void> call = this.buildService(Services.AllArticleFeedService.class, BASE_URL).load(
//                getAuthString(),
//                id
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> getStatusOfArticle(APICallbackListener<Void> listener, int id) {
//        Call<Void> call = this.buildService(Services.StatusArticleService.class, BASE_URL).load(
//                getAuthString(),
//                id
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> getArticle(APICallbackListener<Void> listener, int id) {
//        Call<Void> call = this.buildService(Services.ContentArticleService.class, BASE_URL).load(
//                getAuthString(),
//                id
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    @Override
//    public Call<Void> getSubscriptions(APICallbackListener<Void> listener) {
//        Call<Void> call = this.buildService(Services.SubscriptionsService.class, BASE_URL).load(
//                getAuthString()
//        );
//        call.enqueue(createAPICallback(listener));
//
//        return call;
//    }
//
//    public static String username = "undeuxtrois";
//    public static String pass = "123456";
//
//    private String getAuthString() {
//        String mdd5 = getMD5EncryptedString(username + pass);
//
//        Log.e("REG", username + pass);
//        Log.e("MD5", mdd5);
//
//        return mdd5;
//    }
//
//    private String getMD5String(String string) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
//            digest.update(string.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuilder hexString = new StringBuilder();
//            for (byte aMessageDigest : messageDigest)
//                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String getMD5EncryptedString(String encTarget){
//        MessageDigest mdEnc = null;
//        try {
//            mdEnc = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println("Exception while encrypting to md5");
//            e.printStackTrace();
//        } // Encryption algorithm
//        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
//        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
//        while ( md5.length() < 32 ) {
//            md5 = "0"+md5;
//        }
//        return md5;
//    }
//
//    public static String getbase64String(String s) throws UnsupportedEncodingException {
//        byte[] data = s.getBytes("UTF-8");
//
//        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
//        return base64;
//    }
//
//    public static String getStringFromBase64(String s) throws UnsupportedEncodingException {
//        byte[] data = Base64.decode(s, Base64.DEFAULT);
//        String text = new String(data, "UTF-8");
//        return text;
//    }

    /**
     * Creates a Retrofit Service from a Service class
     *
     * @param serviceClass Service class defining the API call
     * @param baseURL base URL for API calls
     * @param <S> Data model returned in the callback
     * @return new service instance
     */
    private <S> S buildService(Class<S> serviceClass, String baseURL) {

        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
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
