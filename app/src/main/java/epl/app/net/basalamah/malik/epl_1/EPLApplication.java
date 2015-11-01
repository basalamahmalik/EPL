package epl.app.net.basalamah.malik.epl_1;

import android.app.Application;
import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by Malik on 19-Oct-15.
 */
public class EPLApplication extends Application {

    private static EPLApplication instance = new EPLApplication();

    public EPLApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
    public static AccessToken accessToken;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Parse.enableLocalDatastore(this);
        Fresco.initialize(this);
        accessToken = AccessToken.getCurrentAccessToken();
        Parse.initialize(this, AppConstants.APPLICATION_ID, AppConstants.CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseObject.registerSubclass(ParseTest.class);
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                } else {
                }
            }
        });

    }
    }




