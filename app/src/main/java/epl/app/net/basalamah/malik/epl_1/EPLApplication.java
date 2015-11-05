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
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Malik on 19-Oct-15.
 */
public class EPLApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "VIbBGVCc9uoM8jXQY8UsSw";
    private static final String TWITTER_SECRET = "V1K44eJUxQfb7Gt6v7HO7hFyHp9CiXfkHLvK4SWiZLg";


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
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
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




