package com.project.indotuber.singleton;

/**
 * Created by yoasfs on 8/3/15.
 */
public class ServerManager {
    private static ServerManager SERVERMANAGER = null;
    public static final String TAG = ServerManager.class.getSimpleName();
    public static ServerManager getInstance() {
        if (SERVERMANAGER == null) {
            SERVERMANAGER = new ServerManager();
        }
        return SERVERMANAGER;
    }
}

