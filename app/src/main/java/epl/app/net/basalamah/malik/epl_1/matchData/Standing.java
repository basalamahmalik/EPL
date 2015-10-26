package epl.app.net.basalamah.malik.epl_1.MatchData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik on 04-Oct-15.
 */


   public class Standing {

    @SerializedName("teams")
    @Expose
    private List<Team> teams = new ArrayList<Team>();

    /**
     *
     * @return
     * The teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     * The teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

}


