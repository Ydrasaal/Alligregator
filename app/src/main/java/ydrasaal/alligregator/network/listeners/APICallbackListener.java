package ydrasaal.alligregator.network.listeners;

import retrofit.Response;

/**
 * Created by Léo on 12/03/2016.
 */
public interface APICallbackListener<T> {

    void onResponseSuccess(Response<T> response);

    void onResponseFailure();

    void onFailure();
}
