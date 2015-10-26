package epl.app.net.basalamah.malik.epl_1.MatchData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Malik on 13-Sep-15.
 */


  public class MatchEvent {

  @SerializedName("event_id")
  @Expose
  private String eventId;
  @SerializedName("event_match_id")
  @Expose
  private String eventMatchId;
  @SerializedName("event_type")
  @Expose
  private String eventType;
  @SerializedName("event_minute")
  @Expose
  private String eventMinute;
  @SerializedName("event_team")
  @Expose
  private String eventTeam;
  @SerializedName("event_player")
  @Expose
  private String eventPlayer;
  @SerializedName("event_player_id")
  @Expose
  private String eventPlayerId;
  @SerializedName("event_result")
  @Expose
  private String eventResult;

  /**
   *
   * @return
   * The eventId
   */
  public String getEventId() {
   return eventId;
  }

  /**
   *
   * @param eventId
   * The event_id
   */
  public void setEventId(String eventId) {
   this.eventId = eventId;
  }

  /**
   *
   * @return
   * The eventMatchId
   */
  public String getEventMatchId() {
   return eventMatchId;
  }

  /**
   *
   * @param eventMatchId
   * The event_match_id
   */
  public void setEventMatchId(String eventMatchId) {
   this.eventMatchId = eventMatchId;
  }

  /**
   *
   * @return
   * The eventType
   */
  public String getEventType() {
   return eventType;
  }

  /**
   *
   * @param eventType
   * The event_type
   */
  public void setEventType(String eventType) {
   this.eventType = eventType;
  }

  /**
   *
   * @return
   * The eventMinute
   */
  public String getEventMinute() {
   return eventMinute;
  }

  /**
   *
   * @param eventMinute
   * The event_minute
   */
  public void setEventMinute(String eventMinute) {
   this.eventMinute = eventMinute;
  }

  /**
   *
   * @return
   * The eventTeam
   */
  public String getEventTeam() {
   return eventTeam;
  }

  /**
   *
   * @param eventTeam
   * The event_team
   */
  public void setEventTeam(String eventTeam) {
   this.eventTeam = eventTeam;
  }

  /**
   *
   * @return
   * The eventPlayer
   */
  public String getEventPlayer() {
   return eventPlayer;
  }

  /**
   *
   * @param eventPlayer
   * The event_player
   */
  public void setEventPlayer(String eventPlayer) {
   this.eventPlayer = eventPlayer;
  }

  /**
   *
   * @return
   * The eventPlayerId
   */
  public String getEventPlayerId() {
   return eventPlayerId;
  }

  /**
   *
   * @param eventPlayerId
   * The event_player_id
   */
  public void setEventPlayerId(String eventPlayerId) {
   this.eventPlayerId = eventPlayerId;
  }

  /**
   *
   * @return
   * The eventResult
   */
  public String getEventResult() {
   return eventResult;
  }

  /**
   *
   * @param eventResult
   * The event_result
   */
  public void setEventResult(String eventResult) {
   this.eventResult = eventResult;
  }

}