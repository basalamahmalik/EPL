package epl.app.net.basalamah.malik.epl_1.MatchData.Facebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik on 08-Nov-15.
 */
public class FacebookAlbum {

        @SerializedName("data")
        @Expose
        private List<AlbumData> data = new ArrayList<AlbumData>();

        /**
         *
         * @return
         * The data
         */
        public List<AlbumData> getData() {
                return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<AlbumData> data) {
                this.data = data;
        }
}
