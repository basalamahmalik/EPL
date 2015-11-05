package epl.app.net.basalamah.malik.epl_1.Social;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import epl.app.net.basalamah.malik.epl_1.EPLApplication;
import epl.app.net.basalamah.malik.epl_1.MatchData.MatchData;
import epl.app.net.basalamah.malik.epl_1.R;

/**
 * Created by Malik on 03-Nov-15.
 */
public class SocialFacebook {

    Activity activity;
    Context context;
    CallbackManager callbackManager;
    LoginButton loginButton;
    String token;
    MatchData match_data;



    public SocialFacebook(Activity activity, Context context , CallbackManager callbackManager ,MatchData match_data ) {
        this.activity=activity;
        this.context=context;
        this.callbackManager=callbackManager;

    }


}
