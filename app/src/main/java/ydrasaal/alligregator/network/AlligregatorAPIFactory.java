package ydrasaal.alligregator.network;

import ydrasaal.alligregator.network.retrofit.AlligregatorRetrofitAPI;

/**
 * Created by Léo on 12/03/2016.
 *
 * Factory creating the current API implementaion Singleton object
 */
public class AlligregatorAPIFactory {

    public static IAlligregatorAPI   getAPIImplementation() {
        return new AlligregatorRetrofitAPI();
    }
}
