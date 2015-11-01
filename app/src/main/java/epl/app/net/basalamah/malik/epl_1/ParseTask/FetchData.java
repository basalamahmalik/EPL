package epl.app.net.basalamah.malik.epl_1.ParseTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import epl.app.net.basalamah.malik.epl_1.API.DataChangeListener;
import epl.app.net.basalamah.malik.epl_1.Adapters.MatchesListAdapter;
import epl.app.net.basalamah.malik.epl_1.Adapters.StandingListAdapter;
import epl.app.net.basalamah.malik.epl_1.AppConstants;
import epl.app.net.basalamah.malik.epl_1.MatchData.Match;
import epl.app.net.basalamah.malik.epl_1.MatchData.MatchData;
import epl.app.net.basalamah.malik.epl_1.MatchData.MatchEvent;
import epl.app.net.basalamah.malik.epl_1.MatchData.Standing;
import epl.app.net.basalamah.malik.epl_1.MatchData.Team;
import epl.app.net.basalamah.malik.epl_1.R;

/**
 * Created by Malik on 07-Sep-15.
 */

public class FetchData implements DataChangeListener {

    Gson gson = new Gson();
    List<Match> listDataHeader = new ArrayList<>();
    HashMap<Match, List<MatchEvent>> listDataChild = new HashMap<>();
    Activity activity;
    public MatchData match_data;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    MatchesListAdapter listAdapter;
    StandingListAdapter adapter;
    Standing standing_data;
    public List<Team> listData = new ArrayList<>();
    String ID;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    Date localUpdate;

    public FetchData(){}


    public FetchData(Context context, Activity activity, MatchesListAdapter adapter, SwipeRefreshLayout swipeRefreshLayout, SharedPreferences sp , SharedPreferences.Editor editor) {
        this.listAdapter = adapter;
        this.activity = activity;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.context = context;
        this.sp = sp;
        this. editor = editor;
    }

    public FetchData(Activity activity, Context context, SwipeRefreshLayout swipeRefreshLayout, StandingListAdapter adapter , SharedPreferences sp , SharedPreferences.Editor editor) {
        this.activity = activity;
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.adapter = adapter;
        this.sp = sp;
        this. editor = editor;
    }

    public void ParseCom(final String ACTION) {
        if(ACTION.equals(AppConstants.STANDING)){
            ID = AppConstants.EPL_STANDINGD_ID;
        }else if(ACTION.equals(AppConstants.FIXTURE)){
            ID = AppConstants.EPL_FIXTURE_ID;
        }
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("JSON");
            query.getInBackground(ID, new GetCallback<ParseObject>() {

                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        final Date serverDataUpdate = object.getUpdatedAt();

                        if(ACTION.equals(AppConstants.STANDING)){
                            localUpdate = getStandingLocalDate();

                        }else if(ACTION.equals(AppConstants.FIXTURE)){
                            localUpdate = getFixtureLocalDate();
                        }
                        if (isDateAfter(localUpdate , serverDataUpdate)) {

                        ParseFile file = (ParseFile) object.get("JSON");
                        file.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    // data has the bytes for the resume
                                    String JSON = new String(data);
                                    try {
                                        if(ACTION.equals(AppConstants.STANDING)){
                                            standing_data = gson.fromJson(JSON, new TypeToken<Standing>() {
                                            }.getType());
                                            standingChangeCallback(standing_data, serverDataUpdate.getTime());
                                            //setStandingData(context, standing_data);
                                            //standing_data=getStandingData(context);
                                            //PrepareStanding();

                                        }else if(ACTION.equals(AppConstants.FIXTURE)){
                                            match_data = gson.fromJson(JSON, new TypeToken<MatchData>() {}.getType());
                                            //setFixtureData(context,match_data);
                                            fixtureChangeCallback(match_data,serverDataUpdate.getTime());
                                            //match_data=getFixtureData(context);
                                            //PrepareFixture();
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                    }
                                } else {
                                    Failure();
                                    // something went wrong
                                }
                            }
                        });

                    } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(context, "no Update Available  ", Toast.LENGTH_SHORT).show();

                        }

                    }else{
                            Failure();
                    }
                }
            });
        } catch (Exception ex) {
        ex.printStackTrace();
        }
    }
    public Date getFixtureLocalDate(){
        //If there is no modification date exist return Date(0) i.e 1970
        return new Date(sp.getLong(AppConstants.DATA_EPL_FIXTURES_LAST_MODIFICATION_DATE_KEY , new Date(0).getTime()));
    }
    public Date getStandingLocalDate(){
        //If there is no modification date exist return Date(0) i.e 1970
        return new Date(sp.getLong(AppConstants.DATA_EPL_STANDINGS_LAST_MODIFICATION_DATE_KEY , new Date(0).getTime()));
    }

    public void setFixtureData(Context context, MatchData data){

        String fullPath = context.getFilesDir().getAbsolutePath()+ "/" + AppConstants.DATA_EPL_FIXTURES ;//Internal Memory
        String fixtureDataToJSON = gson.toJson(data);
        writeToFile(fullPath ,fixtureDataToJSON);
    }

    public MatchData getFixtureData(Context context){
        String fullPath = context.getFilesDir().getAbsolutePath()+ "/" + AppConstants.DATA_EPL_FIXTURES ;//Internal Memory
        if(match_data == null) {
            String fixtureDataFromJSON = ReadFromfile(fullPath);
            match_data = gson.fromJson(fixtureDataFromJSON, new TypeToken<MatchData>() {}.getType());
        }
        return match_data;
    }

    public void setStandingData(Context context, Standing data){

        String fullPath = context.getFilesDir().getAbsolutePath()+ "/" + AppConstants.DATA_EPL_STANDINGS ;//Internal Memory
        String StandingDataToJSON = gson.toJson(data);
        writeToFile(fullPath ,StandingDataToJSON);
    }

    public Standing getStandingData(Context context){
        String fullPath = context.getFilesDir().getAbsolutePath()+ "/" + AppConstants.DATA_EPL_STANDINGS ;//Internal Memory
        if(standing_data == null) {
            String StandingDataFromJSON = ReadFromfile(fullPath);
            standing_data = gson.fromJson(StandingDataFromJSON, new TypeToken<Standing>() {}.getType());
        }
        return standing_data;
    }


    public void PrepareFixture(MatchData data) {

        if(!(data.getMatches() == null)) {
            listDataHeader = data.getMatches();

            for (int i = 0; i < listDataHeader.size(); i++) {
                listDataChild.put(listDataHeader.get(i), listDataHeader.get(i).getMatchEvents());
            }

            listAdapter.setData(listDataHeader, listDataChild);
            listAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    public void PrepareStanding(Standing data){
        
        if(!(data.getTeams() == null)) {
            data.getTeams();
            listData = data.getTeams();
            adapter.setData(listData);
            //sListener.standingData(listData);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    public void Failure(){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(context.getResources().getString(R.string.error));
        alertDialog.setMessage(context.getResources().getString(R.string.no_data));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void writeToFile(String filePath , String content){
        final File dir = new File(context.getFilesDir() +AppConstants.RESOURCES_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs(); //create folders where write files
        }
        OutputStream out= null;
        try {
            out = new FileOutputStream(filePath);
            out.write(content.getBytes());
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String ReadFromfile(String fileName) {

        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {

            fIn = new FileInputStream(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }

        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }


    @Override
    public void fixtureChangeCallback(MatchData match, long lastFixtureUpdate) {
        setFixtureData(context,match);
        setLastFixtureUpdateDate(lastFixtureUpdate);
    }

    @Override
    public void standingChangeCallback(Standing standing, long lastStandingUpdate) {
        setStandingData(context, standing);
        setLastStandingUpdateDate(lastStandingUpdate);
    }

    public void setLastFixtureUpdateDate (long lastFixtureUpdate){
        editor.putLong(AppConstants.DATA_EPL_FIXTURES_LAST_MODIFICATION_DATE_KEY, lastFixtureUpdate);
        editor.commit();
    }

    public void setLastStandingUpdateDate(long lastStandingUpdate){
        editor.putLong(AppConstants.DATA_EPL_STANDINGS_LAST_MODIFICATION_DATE_KEY, lastStandingUpdate);
        editor.commit();
    }


        private boolean isDateAfter(Date date1 , Date date2){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        return c2.after(c1);
    }
}



/*    public void Connection(String ACTION){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        API standing = retrofit.create(API.class);

        if(ACTION == AppConstants.FIXTURE){
            Call<MatchData> call = standing.getFixture(ACTION, AppConstants.API_KEY, AppConstants.COMP_ID, AppConstants.From_Date,AppConstants.To_Date);
            call.enqueue(new Callback<MatchData>() {
                @Override
                public void onResponse(Response<MatchData> response, Retrofit retrofit) {
                    match_data = response.body();
                    PrepareFixture();
                }
                @Override
                public void onFailure(Throwable t) {
                    // IP or No Data in Server...
                    Failure();
                }
            });
        }else{
            Call<Standing> call = standing.getStanding(ACTION,AppConstants.API_KEY,AppConstants.COMP_ID);
            call.enqueue(new Callback<Standing>() {
                @Override
                public void onResponse(Response<Standing> response, Retrofit retrofit) {
                    standing_data = response.body();
                    PrepareStanding();
                }

                @Override
                public void onFailure(Throwable t) {
                    // IP or No Data in Server...
                    Failure();

                }
            });
        }
    }*/