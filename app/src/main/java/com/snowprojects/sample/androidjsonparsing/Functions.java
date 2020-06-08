package com.snowprojects.sample.androidjsonparsing;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Snow Corp Team on 06/06/20 at 6:23 PM.
 * support@snowcorp.org
 * www.snowcorp.org
 */

public class Functions {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
