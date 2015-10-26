package epl.app.net.basalamah.malik.epl_1.MatchData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik on 13-Sep-15.
 */


public class MatchData {
        @SerializedName("matches")
        @Expose
        private List<Match> matches = new ArrayList<Match>();

        /**
         *
         * @return
         * The matches
         */
        public List<Match> getMatches() {
                return matches;
        }

        /**
         *
         * @param matches
         * The matches
         */
        public void setMatches(List<Match> matches) {
                this.matches = matches;
        }

}
