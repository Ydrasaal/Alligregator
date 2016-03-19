package ydrasaal.alligregator.network;

/**
 * Created by Léo on 12/03/2016.
 *
 * Singleton encapsulating the current app's API implementation
 */
public class AlligregatorAPI {

    private static IAlligregatorAPI ourInstance = AlligregatorAPIFactory.getAPIImplementation();

    public static IAlligregatorAPI getInstance() {
        return ourInstance;
    }
}
