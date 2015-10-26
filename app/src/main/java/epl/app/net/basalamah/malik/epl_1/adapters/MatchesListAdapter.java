package epl.app.net.basalamah.malik.epl_1.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import epl.app.net.basalamah.malik.epl_1.AppConstants;
import epl.app.net.basalamah.malik.epl_1.MatchData.Match;
import epl.app.net.basalamah.malik.epl_1.MatchData.MatchEvent;
import epl.app.net.basalamah.malik.epl_1.MatchData.Standing;
import epl.app.net.basalamah.malik.epl_1.ParseTask.FetchData;
import epl.app.net.basalamah.malik.epl_1.R;

/**
 * Created by Malik on 07-Sep-15.
 */
public class MatchesListAdapter extends BaseExpandableListAdapter {

    Uri home,away;
    Standing standing = new Standing();

    static class ViewHolder {
        TextView eventText;
        TextView eventMinute;
        TextView teamHome;
        TextView teamAway;
        TextView matchTime;
        TextView result;
        TextView liveStatus;
        SimpleDraweeView iconHome;
        SimpleDraweeView iconAway;
        SimpleDraweeView eventIcon;


    }

    private Context _context;
    private List<Match> _listDataHeader;
    private HashMap<Match, List<MatchEvent>> _listDataChild;
    private Activity activity;

    public void setData(List<Match> listDataHeader, HashMap<Match, List<MatchEvent>> listChildData){
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public MatchesListAdapter(Activity activity,Context context, List<Match> listDataHeader, HashMap<Match, List<MatchEvent>> listChildData) {
        this. activity = activity;
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final MatchEvent childText = (MatchEvent) getChild(groupPosition, childPosition);
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_matches, null);
            holder = new ViewHolder();
            holder.eventIcon = (SimpleDraweeView) convertView.findViewById(R.id.eventImage);
            holder.eventText = (TextView) convertView.findViewById(R.id.eventText);
            holder.eventMinute = (TextView) convertView.findViewById(R.id.eventMinut);
            convertView.setTag(holder);
        }else{
        holder = (ViewHolder) convertView.getTag();}

        holder.eventText.setText(childText.getEventPlayer()+""+childText.getEventResult());

        if(childText.getEventTeam().contains("localteam")){
            holder.eventText.setTextColor(Color.BLACK);
        }else{
            holder.eventText.setTextColor(Color.GRAY);
        }

        holder.eventMinute.setText("'"+childText.getEventMinute());

        if(childText.getEventType().contains("yellowcard")){
            Uri imageUri = Uri.parse(AppConstants.yellow_card);
            holder.eventIcon.setImageURI(imageUri);
        }if(childText.getEventType().contains("redcard")){
            Uri imageUri = Uri.parse(AppConstants.red_card);
            holder.eventIcon.setImageURI(imageUri);
        }if(childText.getEventType().contains("goal")){
            Uri imageUri = Uri.parse(AppConstants.goal);
            holder.eventIcon.setImageURI(imageUri);
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {

        final Dialog dialog = new Dialog(activity);
        final Match headerTitle = (Match) getGroup(groupPosition);
        final ViewHolder holder;


        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.list_group, null);
            holder = new ViewHolder();

            holder.iconHome = (SimpleDraweeView) convertView.findViewById(R.id.imageTeam1);
            holder.iconAway = (SimpleDraweeView) convertView.findViewById(R.id.imageTeam2);


            holder.teamHome = (TextView) convertView.findViewById(R.id.textTeam1);
            holder.teamAway = (TextView) convertView.findViewById(R.id.textTeam2);
            holder.matchTime= (TextView) convertView.findViewById(R.id.textMatchTime);
            holder.result   = (TextView) convertView.findViewById(R.id.textResult);
            holder.liveStatus=(TextView) convertView.findViewById(R.id.textLiveStatus);

            convertView.setTag(holder);
        }else{
        holder = (ViewHolder) convertView.getTag();
        }

        holder.teamHome.setText(headerTitle.getMatchLocalteamName());
        holder.teamAway.setText(headerTitle.getMatchVisitorteamName());
        holder.matchTime.setText(headerTitle.getMatchDate());
        holder.result.setText("(" + headerTitle.getMatchLocalteamScore() + ") v.s (" + headerTitle.getMatchVisitorteamScore() + ")");
        holder.liveStatus.setText(headerTitle.getMatchStatus());

        checkLogos(headerTitle);
        holder.iconHome.setImageURI(home);
        holder.iconAway.setImageURI(away);


        holder.iconHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.custom_dialog);
                SimpleDraweeView ia = (SimpleDraweeView) dialog.findViewById(R.id.TeamHA);
                TextView text = (TextView) dialog.findViewById(R.id.txHA);
                Button dialogButton = (Button) dialog.findViewById(R.id.okbt);
                ArrayList data;
                checkLogos(headerTitle);
                // set the custom dialog components - text, image and button
                data = checkID(headerTitle.getMatchLocalteamName());
                text.setText("Last Results: "+data.get(0)+"\n"+
                                "Wins:"+data.get(1)+"\n"+
                                "Draws:"+data.get(2)+"\n"+
                                "Loses:"+data.get(3)+"\n"+
                                "Score:"+data.get(4)+"\n"+
                                "Receive:"+data.get(5)+"\n"
                );                ia.setImageURI(home);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.iconAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.custom_dialog);
                SimpleDraweeView ib = (SimpleDraweeView) dialog.findViewById(R.id.TeamHA);
                TextView text = (TextView) dialog.findViewById(R.id.txHA);
                Button dialogButton = (Button) dialog.findViewById(R.id.okbt);
                ArrayList data;
                checkLogos(headerTitle);
                // set the custom dialog components - text, image and button
                data = checkID(headerTitle.getMatchVisitorteamName());
                text.setText("Last Results: "+data.get(0)+"\n"+
                             "Wins:"+data.get(1)+"\n"+
                             "Draws:"+data.get(2)+"\n"+
                             "Loses:"+data.get(3)+"\n"+
                             "Score:"+data.get(4)+"\n"+
                             "Receive:"+data.get(5)+"\n"
                );
                ib.setImageURI(away);


                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public ArrayList checkID(String headerTitle){
        ArrayList data = new ArrayList();
        FetchData s = new FetchData();
        standing=s.getStandingData(_context);

        for(int i =0; i<standing.getTeams().size(); i++){
         if( standing.getTeams().get(i).getStandTeamName().equals(headerTitle)) {
             data.add(standing.getTeams().get(i).getStandRecentForm());
             data.add(standing.getTeams().get(i).getStandOverallW());
             data.add(standing.getTeams().get(i).getStandOverallD());
             data.add(standing.getTeams().get(i).getStandOverallL());
             data.add(standing.getTeams().get(i).getStandOverallGs());
             data.add(standing.getTeams().get(i).getStandOverallGa());
         }
        }
        return data;

    }
    public void checkLogos(Match headerTitle){
        if(headerTitle.getMatchLocalteamName().contains("Manchester United")){
            home = Uri.parse(AppConstants.manchester_united);
        }if(headerTitle.getMatchVisitorteamName().contains("Manchester United")){
            away = Uri.parse(AppConstants.manchester_united);
        }

        if(headerTitle.getMatchLocalteamName().contains("Manchester City")){
            home = Uri.parse(AppConstants.manchester_city);
        }if(headerTitle.getMatchVisitorteamName().contains("Manchester City")){
            away = Uri.parse(AppConstants.manchester_city);
        }

        if(headerTitle.getMatchLocalteamName().contains("Tottenham")){
            home = Uri.parse(AppConstants.tottenham_hotspur);
        }if(headerTitle.getMatchVisitorteamName().contains("Tottenham")){
            away = Uri.parse(AppConstants.tottenham_hotspur);
        }

        if(headerTitle.getMatchLocalteamName().contains("Everton")){
            home = Uri.parse(AppConstants.everton);
        }if(headerTitle.getMatchVisitorteamName().contains("Everton")){
            away = Uri.parse(AppConstants.everton);
        }

        if(headerTitle.getMatchLocalteamName().contains("Watford")){
            home = Uri.parse(AppConstants.watford);
        }if(headerTitle.getMatchVisitorteamName().contains("Watford")){
            away = Uri.parse(AppConstants.watford);
        }

        if(headerTitle.getMatchLocalteamName().contains("Aston Villa")){
            home = Uri.parse(AppConstants.aston_villa);
        }if(headerTitle.getMatchVisitorteamName().contains("Aston Villa")){
            away = Uri.parse(AppConstants.aston_villa);
        }

        if(headerTitle.getMatchLocalteamName().contains("Leicester")){
            home = Uri.parse(AppConstants.leicester_city);
        }if(headerTitle.getMatchVisitorteamName().contains("Leicester")){
            away = Uri.parse(AppConstants.leicester_city);
        }

        if(headerTitle.getMatchLocalteamName().contains("Sunderland")){
            home = Uri.parse(AppConstants.sunderland);
        }if(headerTitle.getMatchVisitorteamName().contains("Sunderland")){
            away = Uri.parse(AppConstants.sunderland);
        }

        if(headerTitle.getMatchLocalteamName().contains("Chelsea")){
            home = Uri.parse(AppConstants.chelsea);
        }if(headerTitle.getMatchVisitorteamName().contains("Chelsea")){
            away = Uri.parse(AppConstants.chelsea);
        }

        if(headerTitle.getMatchLocalteamName().contains("Swansea")){
            home = Uri.parse(AppConstants.swansea_city);
        }if(headerTitle.getMatchVisitorteamName().contains("Swansea")){
            away = Uri.parse(AppConstants.swansea_city);
        }

        if(headerTitle.getMatchLocalteamName().contains("West Ham")){
            home = Uri.parse(AppConstants.west_ham);
        }if(headerTitle.getMatchVisitorteamName().contains("West Ham")){
            away = Uri.parse(AppConstants.west_ham);
        }

        if(headerTitle.getMatchLocalteamName().contains("Southampton")){
            home     = Uri.parse(AppConstants.southampton);
        }if(headerTitle.getMatchVisitorteamName().contains("Southampton")){
            away = Uri.parse(AppConstants.southampton);
        }

        if(headerTitle.getMatchLocalteamName().contains("Newcastle Utd")){
            home = Uri.parse(AppConstants.newcastle_united);
        }if(headerTitle.getMatchVisitorteamName().contains("Newcastle Utd")){
            away = Uri.parse(AppConstants.newcastle_united);
        }

        if(headerTitle.getMatchLocalteamName().contains("Stoke City")){
            home = Uri.parse(AppConstants.stoke_city);
        }if(headerTitle.getMatchVisitorteamName().contains("Stoke City")){
            away = Uri.parse(AppConstants.stoke_city);
        }

        if(headerTitle.getMatchLocalteamName().contains("Liverpool")){
            home = Uri.parse(AppConstants.liverpool);
        }if(headerTitle.getMatchVisitorteamName().contains("Liverpool")){
            away = Uri.parse(AppConstants.liverpool);
        }

        if(headerTitle.getMatchLocalteamName().contains("Norwich")){
            home = Uri.parse(AppConstants.norwich);
        }if(headerTitle.getMatchVisitorteamName().contains("Norwich")){
            away = Uri.parse(AppConstants.norwich);
        }

        if(headerTitle.getMatchLocalteamName().contains("West Brom")){
            home = Uri.parse(AppConstants.west_bromwich);
        }if(headerTitle.getMatchVisitorteamName().contains("West Brom")){
            away = Uri.parse(AppConstants.west_bromwich);
        }

        if(headerTitle.getMatchLocalteamName().contains("Arsenal")){
            home = Uri.parse(AppConstants.arsenal);
        }if(headerTitle.getMatchVisitorteamName().contains("Arsenal")){
            away = Uri.parse(AppConstants.arsenal);
        }

        if(headerTitle.getMatchLocalteamName().contains("Crystal Palace")){
            home = Uri.parse(AppConstants.crystal_palace);
        }if(headerTitle.getMatchVisitorteamName().contains("Crystal Palace")){
            away = Uri.parse(AppConstants.crystal_palace);
        }

        if(headerTitle.getMatchLocalteamName().contains("Bournemouth")){
            home = Uri.parse(AppConstants.bournemouth);
        }if(headerTitle.getMatchVisitorteamName().contains("Bournemouth")){
            away = Uri.parse(AppConstants.bournemouth);

        }
    }
}