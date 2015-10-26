package epl.app.net.basalamah.malik.epl_1.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import epl.app.net.basalamah.malik.epl_1.Adapters.StandingListAdapter;
import epl.app.net.basalamah.malik.epl_1.AppConstants;
import epl.app.net.basalamah.malik.epl_1.MainActivity;
import epl.app.net.basalamah.malik.epl_1.MatchData.Standing;
import epl.app.net.basalamah.malik.epl_1.MatchData.Team;
import epl.app.net.basalamah.malik.epl_1.ParseTask.FetchData;
import epl.app.net.basalamah.malik.epl_1.R;


public class StandingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    Context context;
    StandingListAdapter listAdapter;


    private BroadcastReceiver internet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MainActivity.InternetConnection(context)) {
                swipeRefreshLayout.setRefreshing(true);
                updateStanding();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.standing_fragment, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        listView = (ListView) rootView.findViewById(R.id.lv);
        listAdapter = new StandingListAdapter(context, new ArrayList<Team>());
        listView.setAdapter(listAdapter);
        updateStanding();

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            swipeRefreshLayout.setRefreshing(true);
            updateStanding();
            Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateStanding() {

        if(!MainActivity.InternetConnection(context)){
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(this.internet, filter);
            createConnection();
        }else {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
            editor = sp.edit();
            FetchData matchTask = new FetchData(this.getActivity(),context, swipeRefreshLayout,listAdapter,sp,editor);
            //matchTask.Connection(AppConstants.STANDING);
            matchTask.ParseCom(AppConstants.STANDING);
            Standing standing = matchTask.getStandingData(context);
            matchTask.PrepareStanding(standing);
            swipeRefreshLayout.setRefreshing(false);

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
    public void onDestroy() {
        super.onDestroy();
        try {
            context.unregisterReceiver(this.internet);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onRefresh() {
        updateStanding();
        Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
    }

}
