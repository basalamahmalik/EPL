package epl.app.net.basalamah.malik.epl_1.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
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
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import epl.app.net.basalamah.malik.epl_1.Adapters.MatchesListAdapter;
import epl.app.net.basalamah.malik.epl_1.AppConstants;
import epl.app.net.basalamah.malik.epl_1.EPLApplication;
import epl.app.net.basalamah.malik.epl_1.MainActivity;
import epl.app.net.basalamah.malik.epl_1.MatchData.Match;
import epl.app.net.basalamah.malik.epl_1.MatchData.MatchData;
import epl.app.net.basalamah.malik.epl_1.MatchData.MatchEvent;
import epl.app.net.basalamah.malik.epl_1.ParseTask.FetchData;
import epl.app.net.basalamah.malik.epl_1.R;
import epl.app.net.basalamah.malik.epl_1.Adapters.ShareIntentListAdapter;


public class FixtureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Gson gson = new Gson();
    Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public MatchesListAdapter listAdapter;
    ExpandableListView expListView;
    SwipeRefreshLayout swipeRefreshLayout;
    MatchData match_data;
    Match match_list;
    List<Match> listDataHeader = new ArrayList<>();
    HashMap<Match, List<MatchEvent>> listDataChild = new HashMap<>();
    ShareDialog shareDialog;
    LoginButton facebookLoginButton;
    TwitterLoginButton twitterLoginButton;
    CallbackManager callbackManager;
    //String match_list;
    AccessTokenTracker accessTokenTracker;
    Boolean facebookLoggedIn = false;
    Boolean twitterLoggedIn = false;
    TwitterSession twitterSession;
    int facebookRequestCode;
    private String facebookLoginToken;

    private BroadcastReceiver internet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MainActivity.InternetConnection(context)) {
                swipeRefreshLayout.setRefreshing(true);
                updateMatches();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity().getApplicationContext();
        shareDialog = new ShareDialog(this);
        twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                    updateWithToken(newAccessToken);

            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());


        if(twitterSession != null){
            twitterLoggedIn = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        final View rootView = inflater.inflate(R.layout.fixture_fragment, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        if(intent.getExtras()!= null){
            try {

                getMatchData(intent.getExtras().getString("PARSE_NOTIFICATION_DATA"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listDataHeader = match_data.getMatches();
            for (int i = 0; i < listDataHeader.size(); i++) {
                listDataChild.put(listDataHeader.get(i), listDataHeader.get(i).getMatchEvents());
            }


            listAdapter = new MatchesListAdapter(getActivity(),context,listDataHeader , listDataChild);
            swipeRefreshLayout.setRefreshing(false);
        }else {
            listAdapter = new MatchesListAdapter(getActivity(), context, new ArrayList<Match>(), new HashMap<Match, List<MatchEvent>>());
            updateMatches();
        }
            expListView.setAdapter(listAdapter);

            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used

             */
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });

            expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    String names[] = {"Share"};
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View convertView = (View) inflater.inflate(android.R.layout.simple_list_item_1, null);
                    alertDialog.setView(convertView);
                    alertDialog.setTitle("Select");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.dialog_list_text, names);
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch(which){
                                case 0:
                                    Share(position);
                                    break;
                            }
                        }
                    });

                    alertDialog.show();

                    return false;
                }
            });


                // Listview Group click listener
                expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()

                                                    {
                                                        @Override
                                                        public boolean onGroupClick(ExpandableListView parent, View v,
                                                                                    int groupPosition, long id) {
                                                            return false;
                                                        }
                                                    }

                );
        // Listview Group expanded listener
                expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()

                                                     {
                                                         @Override
                                                         public void onGroupExpand(int groupPosition) {
                                                         }
                                                     }

                );
                // Listview Group collasped listener
                expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener()

                                                       {
                                                           @Override
                                                           public void onGroupCollapse(int groupPosition) {
                                                           }
                                                       }

                );
                // Listview on child click listener
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()

                                                    {
                                                        @Override
                                                        public boolean onChildClick(ExpandableListView parent, View v,
                                                                                    int groupPosition, int childPosition, long id) {
                                                            return false;
                                                        }
                                                    }

                );


        return rootView;

            }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case AppConstants.FACEBOOK_REQUEST_CODE:
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;

            case TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE:
                twitterLoginButton.onActivityResult(requestCode,resultCode,data);
                break;
        }

    }


    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            facebookLoggedIn = true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
             if (id == R.id.action_refresh) {
            swipeRefreshLayout.setRefreshing(true);
            updateMatches();
            Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id==R.id.action_share) {
                 return true;
             }

        return super.onOptionsItemSelected(item);
}

    public void updateMatches() {

        if(!MainActivity.InternetConnection(context)){
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(this.internet, filter);
            createConnection();
        }else {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
            editor = sp.edit();
            FetchData matchTask = new FetchData(context,this.getActivity(),listAdapter,swipeRefreshLayout,sp,editor);
            //matchTask.Connection(AppConstants.FIXTURE);
            matchTask.ParseCom(AppConstants.FIXTURE);
            MatchData match = matchTask.getFixtureData(context);
            matchTask.PrepareFixture(match);
            swipeRefreshLayout.setRefreshing(false);
            match_data = matchTask.match_data;
        }
    }

    public void Share(final int position){

        match_list=match_data.getMatches().get(position);
        final String match_result = match_list.getMatchLocalteamName()+" (" + match_list.getMatchLocalteamScore() + ") v.s (" + match_list.getMatchVisitorteamScore() + ") "+ match_list.getMatchVisitorteamName()+
                "\n" + match_list.getMatchStatus();

        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List activities = context.getPackageManager().queryIntentActivities(sendIntent, 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Share with...");
        final ShareIntentListAdapter adapter = new ShareIntentListAdapter(getActivity(), R.layout.social_share,activities.toArray());
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResolveInfo info = (ResolveInfo) adapter.getItem(which);
                if (info.activityInfo.packageName.contains("com.facebook.katana")) {
                    if (facebookLoggedIn == true) {
                        facebookLogin(position);
                    } else {
                        shareFacebook(position);
                    }
                } else if (info.activityInfo.name.contains("com.twitter.android.composer.ComposerActivity")) {

                    if (twitterLoggedIn == true) {
                        twitterLogin(position);
                    } else {
                        shareTwitter(position);
                    }

                } else {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, match_result);
                    (getContext()).startActivity(intent);
                }
            }
        });
        builder.create().show();
    }


    private void createConnection(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog.setTitle(context.getResources().getString(R.string.internet_settings));
        // Setting Dialog Message
        alertDialog.setMessage(context.getResources().getString(R.string.internet_state));

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
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        try {
            context.unregisterReceiver(this.internet);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        updateMatches();
        Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
    }

    private MatchData getMatchData(String matchJsonData)throws JSONException{
        match_data = gson.fromJson(matchJsonData, new TypeToken<MatchData>() {}.getType());
        return match_data;
    }

    public void facebookLogin(final int position){
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.facebook_login, null);
        alertDialog.setView(convertView);
        facebookLoginButton = (LoginButton)convertView.findViewById(R.id.login_button);
        facebookLoginButton.setPublishPermissions("publish_actions");
        facebookRequestCode = facebookLoginButton.getRequestCode();
        facebookLoginButton.setFragment(this);
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                facebookLoginToken = loginResult.getAccessToken().getToken();
                editor.putString(AppConstants.FACEBOOK_TOKEN, facebookLoginToken);
                editor.commit();
                shareFacebook(position);
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

    public void shareFacebook(int position){
        match_list=match_data.getMatches().get(position);
        String match_result = match_list.getMatchLocalteamName()+" (" + match_list.getMatchLocalteamScore() + ") v.s (" + match_list.getMatchVisitorteamScore() + ") "+ match_list.getMatchVisitorteamName()+
                "\n" + match_list.getMatchStatus();

        facebookLoginToken = sp.getString(AppConstants.FACEBOOK_TOKEN,facebookLoginToken);

        GraphRequest request = null;
        try {
            request = GraphRequest.newPostRequest(
                    EPLApplication.accessToken,
                    "/feed",
                    new JSONObject("{\"message\":\""+match_result+"\",\"access_token\":\"" + facebookLoginToken + "\"}"),
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            // Insert your code here
                            Toast.makeText(context, "Successfully Shared", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.executeAsync();
    }

    public void twitterLogin(final int position){
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.twitter_login, null);
        alertDialog.setView(convertView);
        twitterLoginButton = (TwitterLoginButton) convertView.findViewById(R.id.login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                twitterSession = result.data;
                Toast.makeText(getActivity().getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                shareTwitter(position);
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Login Failed ", Toast.LENGTH_SHORT).show();
            }

        });

        twitterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }


    public void shareTwitter(int position){
        match_list=match_data.getMatches().get(position);
        final String match_result = match_list.getMatchLocalteamName()+" (" + match_list.getMatchLocalteamScore() + ") v.s (" + match_list.getMatchVisitorteamScore() + ") "+ match_list.getMatchVisitorteamName()+
                "\n" + match_list.getMatchStatus();


        StatusesService statusesService = Twitter.getApiClient(twitterSession).getStatusesService();
        statusesService.update(match_result, null, null, null, null, null, null, null, null, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {

                Toast.makeText(context, "Shared Successfully ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(context, "Shared Failed ", Toast.LENGTH_LONG).show();
            }
        });

/*          TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                .text(match_result);
                builder.show();*/

    }

}