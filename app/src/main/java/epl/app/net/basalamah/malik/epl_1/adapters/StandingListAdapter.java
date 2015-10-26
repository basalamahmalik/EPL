package epl.app.net.basalamah.malik.epl_1.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import epl.app.net.basalamah.malik.epl_1.MatchData.Team;
import epl.app.net.basalamah.malik.epl_1.R;

/**
 * Created by Malik on 04-Oct-15.
 */

public class StandingListAdapter extends BaseAdapter {

    static class ViewHolder {
        TextView po;
        TextView team;
        TextView play;
        TextView win;
        TextView draw;
        TextView lose;
        TextView points;
    }

    private Context _context;
    private List<Team> _listData;

    public void setData(List<Team> listDataHeader){
        this._listData = listDataHeader;
    }

    public StandingListAdapter(Context context, List<Team> listDataHeader) {
        this._context = context;
        this._listData = listDataHeader;
    }


    @Override
    public Object getItem(int groupPosition) {
        return this._listData.get(groupPosition);
    }

    @Override
    public int getCount() {
        return this._listData.size();
    }

    @Override
    public long getItemId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Team data = (Team) getItem(position);

        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.list_item_standing, null);

            ViewHolder holder = new ViewHolder();

            holder.po = (TextView)convertView.findViewById(R.id.po);
            holder.team = (TextView)convertView.findViewById(R.id.team);
            holder.play = (TextView)convertView.findViewById(R.id.play);
            holder.win = (TextView)convertView.findViewById(R.id.win);
            holder.draw = (TextView)convertView.findViewById(R.id.draw);
            holder.lose = (TextView)convertView.findViewById(R.id.lose);
            holder.points = (TextView)convertView.findViewById(R.id.points);

            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        if (position == 0 || position == 1 || position == 2 || position == 3 ){
            holder.po.setTextColor(Color.BLUE);
            holder.team.setTextColor(Color.BLUE);
            holder.play.setTextColor(Color.BLUE);
            holder.win.setTextColor(Color.BLUE);
            holder.draw.setTextColor(Color.BLUE);
            holder.lose.setTextColor(Color.BLUE);
            holder.points.setTextColor(Color.BLUE);
        }
        if (position == 4 || position == 5 || position == 6 ){
            holder.po.setTextColor(Color.DKGRAY);
            holder.team.setTextColor(Color.DKGRAY);
            holder.play.setTextColor(Color.DKGRAY);
            holder.win.setTextColor(Color.DKGRAY);
            holder.draw.setTextColor(Color.DKGRAY);
            holder.lose.setTextColor(Color.DKGRAY);
            holder.points.setTextColor(Color.DKGRAY);
        }
        if (position == 17 || position == 18 || position == 19 ){
            holder.po.setTextColor(Color.RED);
            holder.team.setTextColor(Color.RED);
            holder.play.setTextColor(Color.RED);
            holder.win.setTextColor(Color.RED);
            holder.draw.setTextColor(Color.RED);
            holder.lose.setTextColor(Color.RED);
            holder.points.setTextColor(Color.RED);
        }

        holder.po.setText(data.getStandPosition());
        holder.team.setText(data.getStandTeamName());
        holder.play.setText(data.getStandOverallGp());
        holder.win.setText(data.getStandOverallW());
        holder.draw.setText(data.getStandOverallD());
        holder.lose.setText(data.getStandOverallL());
        holder.points.setText(data.getStandPoints());

        return convertView;
    }

}
