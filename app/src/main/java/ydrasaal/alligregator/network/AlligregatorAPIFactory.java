package ydrasaal.alligregator.network;

import ydrasaal.alligregator.network.retrofit.AlligregatorRetrofitAPI;

/**
 * Created by Léo on 12/03/2016.
 */
public class AlligregatorAPIFactory {

    public static IAlligregatorAPI   getAPIImplementation() {
        return new AlligregatorRetrofitAPI();
    }
}
