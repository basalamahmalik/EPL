package epl.app.net.basalamah.malik.epl_1.MatchData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik on 13-Sep-15.
 *
 */

 public class Match {
 @SerializedName("match_id")
 @Expose
 private String matchId;
 @SerializedName("match_static_id")
 @Expose
 private String matchStaticId;
 @SerializedName("match_comp_id")
 @Expose
 private String matchCompId;
 @SerializedName("match_date")
 @Expose
 private String matchDate;
 @SerializedName("match_formatted_date")
 @Expose
 private String matchFormattedDate;
 @SerializedName("match_season_beta")
 @Expose
 private String matchSeasonBeta;
 @SerializedName("match_week_beta")
 @Expose
 private String matchWeekBeta;
 @SerializedName("match_venue_beta")
 @Expose
 private String matchVenueBeta;
 @SerializedName("match_venue_id_beta")
 @Expose
 private String matchVenueIdBeta;
 @SerializedName("match_venue_city_beta")
 @Expose
 private String matchVenueCityBeta;
 @SerializedName("match_status")
 @Expose
 private String matchStatus;
 @SerializedName("match_timer")
 @Expose
 private String matchTimer;
 @SerializedName("match_time")
 @Expose
 private String matchTime;
 @SerializedName("match_commentary_available")
 @Expose
 private String matchCommentaryAvailable;
 @SerializedName("match_localteam_id")
 @Expose
 private String matchLocalteamId;
 @SerializedName("match_localteam_name")
 @Expose
 private String matchLocalteamName;
 @SerializedName("match_localteam_score")
 @Expose
 private String matchLocalteamScore;
 @SerializedName("match_visitorteam_id")
 @Expose
 private String matchVisitorteamId;
 @SerializedName("match_visitorteam_name")
 @Expose
 private String matchVisitorteamName;
 @SerializedName("match_visitorteam_score")
 @Expose
 private String matchVisitorteamScore;
 @SerializedName("match_ht_score")
 @Expose
 private String matchHtScore;
 @SerializedName("match_ft_score")
 @Expose
 private String matchFtScore;
 @SerializedName("match_et_score")
 @Expose
 private String matchEtScore;
 @SerializedName("match_events")
 @Expose
 private List<MatchEvent> matchEvents = new ArrayList<MatchEvent>();

 /**
  *
  * @return
  * The matchId
  */
 public String getMatchId() {
  return matchId;
 }

 /**
  *
  * @param matchId
  * The match_id
  */
 public void setMatchId(String matchId) {
  this.matchId = matchId;
 }

 /**
  *
  * @return
  * The matchStaticId
  */
 public String getMatchStaticId() {
  return matchStaticId;
 }

 /**
  *
  * @param matchStaticId
  * The match_static_id
  */
 public void setMatchStaticId(String matchStaticId) {
  this.matchStaticId = matchStaticId;
 }

 /**
  *
  * @return
  * The matchCompId
  */
 public String getMatchCompId() {
  return matchCompId;
 }

 /**
  *
  * @param matchCompId
  * The match_comp_id
  */
 public void setMatchCompId(String matchCompId) {
  this.matchCompId = matchCompId;
 }

 /**
  *
  * @return
  * The matchDate
  */
 public String getMatchDate() {
  return matchDate;
 }

 /**
  *
  * @param matchDate
  * The match_date
  */
 public void setMatchDate(String matchDate) {
  this.matchDate = matchDate;
 }

 /**
  *
  * @return
  * The matchFormattedDate
  */
 public String getMatchFormattedDate() {
  return matchFormattedDate;
 }

 /**
  *
  * @param matchFormattedDate
  * The match_formatted_date
  */
 public void setMatchFormattedDate(String matchFormattedDate) {
  this.matchFormattedDate = matchFormattedDate;
 }

 /**
  *
  * @return
  * The matchSeasonBeta
  */
 public String getMatchSeasonBeta() {
  return matchSeasonBeta;
 }

 /**
  *
  * @param matchSeasonBeta
  * The match_season_beta
  */
 public void setMatchSeasonBeta(String matchSeasonBeta) {
  this.matchSeasonBeta = matchSeasonBeta;
 }

 /**
  *
  * @return
  * The matchWeekBeta
  */
 public String getMatchWeekBeta() {
  return matchWeekBeta;
 }

 /**
  *
  * @param matchWeekBeta
  * The match_week_beta
  */
 public void setMatchWeekBeta(String matchWeekBeta) {
  this.matchWeekBeta = matchWeekBeta;
 }

 /**
  *
  * @return
  * The matchVenueBeta
  */
 public String getMatchVenueBeta() {
  return matchVenueBeta;
 }

 /**
  *
  * @param matchVenueBeta
  * The match_venue_beta
  */
 public void setMatchVenueBeta(String matchVenueBeta) {
  this.matchVenueBeta = matchVenueBeta;
 }

 /**
  *
  * @return
  * The matchVenueIdBeta
  */
 public String getMatchVenueIdBeta() {
  return matchVenueIdBeta;
 }

 /**
  *
  * @param matchVenueIdBeta
  * The match_venue_id_beta
  */
 public void setMatchVenueIdBeta(String matchVenueIdBeta) {
  this.matchVenueIdBeta = matchVenueIdBeta;
 }

 /**
  *
  * @return
  * The matchVenueCityBeta
  */
 public String getMatchVenueCityBeta() {
  return matchVenueCityBeta;
 }

 /**
  *
  * @param matchVenueCityBeta
  * The match_venue_city_beta
  */
 public void setMatchVenueCityBeta(String matchVenueCityBeta) {
  this.matchVenueCityBeta = matchVenueCityBeta;
 }

 /**
  *
  * @return
  * The matchStatus
  */
 public String getMatchStatus() {
  return matchStatus;
 }

 /**
  *
  * @param matchStatus
  * The match_status
  */
 public void setMatchStatus(String matchStatus) {
  this.matchStatus = matchStatus;
 }

 /**
  *
  * @return
  * The matchTimer
  */
 public String getMatchTimer() {
  return matchTimer;
 }

 /**
  *
  * @param matchTimer
  * The match_timer
  */
 public void setMatchTimer(String matchTimer) {
  this.matchTimer = matchTimer;
 }

 /**
  *
  * @return
  * The matchTime
  */
 public String getMatchTime() {
  return matchTime;
 }

 /**
  *
  * @param matchTime
  * The match_time
  */
 public void setMatchTime(String matchTime) {
  this.matchTime = matchTime;
 }

 /**
  *
  * @return
  * The matchCommentaryAvailable
  */
 public String getMatchCommentaryAvailable() {
  return matchCommentaryAvailable;
 }

 /**
  *
  * @param matchCommentaryAvailable
  * The match_commentary_available
  */
 public void setMatchCommentaryAvailable(String matchCommentaryAvailable) {
  this.matchCommentaryAvailable = matchCommentaryAvailable;
 }

 /**
  *
  * @return
  * The matchLocalteamId
  */
 public String getMatchLocalteamId() {
  return matchLocalteamId;
 }

 /**
  *
  * @param matchLocalteamId
  * The match_localteam_id
  */
 public void setMatchLocalteamId(String matchLocalteamId) {
  this.matchLocalteamId = matchLocalteamId;
 }

 /**
  *
  * @return
  * The matchLocalteamName
  */
 public String getMatchLocalteamName() {
  return matchLocalteamName;
 }

 /**
  *
  * @param matchLocalteamName
  * The match_localteam_name
  */
 public void setMatchLocalteamName(String matchLocalteamName) {
  this.matchLocalteamName = matchLocalteamName;
 }

 /**
  *
  * @return
  * The matchLocalteamScore
  */
 public String getMatchLocalteamScore() {
  return matchLocalteamScore;
 }

 /**
  *
  * @param matchLocalteamScore
  * The match_localteam_score
  */
 public void setMatchLocalteamScore(String matchLocalteamScore) {
  this.matchLocalteamScore = matchLocalteamScore;
 }

 /**
  *
  * @return
  * The matchVisitorteamId
  */
 public String getMatchVisitorteamId() {
  return matchVisitorteamId;
 }

 /**
  *
  * @param matchVisitorteamId
  * The match_visitorteam_id
  */
 public void setMatchVisitorteamId(String matchVisitorteamId) {
  this.matchVisitorteamId = matchVisitorteamId;
 }

 /**
  *
  * @return
  * The matchVisitorteamName
  */
 public String getMatchVisitorteamName() {
  return matchVisitorteamName;
 }

 /**
  *
  * @param matchVisitorteamName
  * The match_visitorteam_name
  */
 public void setMatchVisitorteamName(String matchVisitorteamName) {
  this.matchVisitorteamName = matchVisitorteamName;
 }

 /**
  *
  * @return
  * The matchVisitorteamScore
  */
 public String getMatchVisitorteamScore() {
  return matchVisitorteamScore;
 }

 /**
  *
  * @param matchVisitorteamScore
  * The match_visitorteam_score
  */
 public void setMatchVisitorteamScore(String matchVisitorteamScore) {
  this.matchVisitorteamScore = matchVisitorteamScore;
 }

 /**
  *
  * @return
  * The matchHtScore
  */
 public String getMatchHtScore() {
  return matchHtScore;
 }

 /**
  *
  * @param matchHtScore
  * The match_ht_score
  */
 public void setMatchHtScore(String matchHtScore) {
  this.matchHtScore = matchHtScore;
 }

 /**
  *
  * @return
  * The matchFtScore
  */
 public String getMatchFtScore() {
  return matchFtScore;
 }

 /**
  *
  * @param matchFtScore
  * The match_ft_score
  */
 public void setMatchFtScore(String matchFtScore) {
  this.matchFtScore = matchFtScore;
 }

 /**
  *
  * @return
  * The matchEtScore
  */
 public String getMatchEtScore() {
  return matchEtScore;
 }

 /**
  *
  * @param matchEtScore
  * The match_et_score
  */
 public void setMatchEtScore(String matchEtScore) {
  this.matchEtScore = matchEtScore;
 }

 /**
  *
  * @return
  * The matchEvents
  */
 public List<MatchEvent> getMatchEvents() {
  return matchEvents;
 }

 /**
  *
  * @param matchEvents
  * The match_events
  */
 public void setMatchEvents(List<MatchEvent> matchEvents) {
  this.matchEvents = matchEvents;
 }}

