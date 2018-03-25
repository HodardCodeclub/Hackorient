package rw.hackorient.dequeue;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.orhanobut.hawk.Hawk;

/**
 * Created by miller on 3/24/18.
 */

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        //setting up facebook sdk
//        FacebookSdk.setClientToken(getString(R.string.facebook_client_token));
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
