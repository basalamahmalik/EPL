package epl.app.net.basalamah.malik.epl_1;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
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

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Parse.enableLocalDatastore(this);
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




