package epl.app.net.basalamah.malik.epl_1.API;

import epl.app.net.basalamah.malik.epl_1.MatchData.MatchData;
import epl.app.net.basalamah.malik.epl_1.MatchData.Standing;

/**
 * Created by Malik on 15-Oct-15.
 */
public interface DataChangeListener {

    public void fixtureChangeCallback(MatchData match, long lastUpdate);
    public void standingChangeCallback(Standing stand, long lastUpdate);


}
