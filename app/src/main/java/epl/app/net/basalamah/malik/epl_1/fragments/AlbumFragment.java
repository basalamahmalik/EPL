package epl.app.net.basalamah.malik.epl_1.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;

import epl.app.net.basalamah.malik.epl_1.Adapters.AlbumAdapter;
import epl.app.net.basalamah.malik.epl_1.AppConstants;
import epl.app.net.basalamah.malik.epl_1.EPLApplication;
import epl.app.net.basalamah.malik.epl_1.MainActivity;
import epl.app.net.basalamah.malik.epl_1.MatchData.Facebook.AlbumData;
import epl.app.net.basalamah.malik.epl_1.MatchData.Facebook.FacebookData;
import epl.app.net.basalamah.malik.epl_1.R;

/**
 * Created by Malik on 08-Nov-15.
 */
public class AlbumFragment extends Fragment {

    Gson gson = new Gson();
    Context context;
    LoginButton facebookLoginButton;
    CallbackManager callbackManager;
    String facebookLoginToken;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    FacebookData facebook_data;
    AlbumAdapter adapter;
    ListView list;
    AccessTokenTracker accessTokenTracker;
    Boolean facebookLoggedIn = false;

    private BroadcastReceiver internet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MainActivity.InternetConnection(context)) {

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity().getApplicationContext();
        callbackManager = CallbackManager.Factory.create();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.album_fragment, container, false);

        list = (ListView)rootView.findViewById(R.id.album_list);
        adapter = new AlbumAdapter(context,new ArrayList<AlbumData>());
        list.setAdapter(adapter);

        if (facebookLoggedIn == false) {
            facebookLogin();
        } else {
            getAlbum();
        }


        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            if (facebookLoggedIn == false) {
                facebookLogin();
            } else {
                getAlbum();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            facebookLoggedIn = true;
        }
    }

    private void createConnection(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle(context.getResources().getString(R.string.internet_settings));
        // Setting Dialog Message
        alertDialog.setMessage(context.getResources().getString(R.string.internet_state));
        // Internet Setting Icon to Dialog
        //alertDialog.setIcon(icon);
        // On pressing Settings button
        alertDialog.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void facebookLogin(){
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.facebook_login, null);
        alertDialog.setView(convertView);
        facebookLoginButton = (LoginButton)convertView.findViewById(R.id.login_button);
        facebookLoginButton.setReadPermissions("user_likes", "user_photos");
        facebookLoginButton.setFragment(this);
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                facebookLoginToken = loginResult.getAccessToken().getToken();
                editor.putString(AppConstants.FACEBOOK_TOKEN, facebookLoginToken);
                editor.commit();
                getAlbum();
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Login Canceled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(context, "Login Error", Toast.LENGTH_SHORT).show();
            }
        });

        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void getAlbum(){

        facebookLoginToken = sp.getString(AppConstants.FACEBOOK_TOKEN,facebookLoginToken);

        GraphRequest request = GraphRequest.newGraphPathRequest(
                EPLApplication.accessToken,
                "/"+AppConstants.FACEBOOK_ALBUM_ID,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        String Data = response.getJSONObject().toString();
                        try {
                            getAlbumData(Data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,"Please Refresh",Toast.LENGTH_SHORT).show();
                        }
                        adapter.setData(facebook_data.getPhotos().getData());
                        adapter.notifyDataSetChanged();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "photos{picture,name,likes{name}}");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private FacebookData getAlbumData(String Data)throws JSONException {
        facebook_data = gson.fromJson(Data, new TypeToken<FacebookData>() {}.getType());
        return facebook_data;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            context.unregisterReceiver(this.internet);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }



}
