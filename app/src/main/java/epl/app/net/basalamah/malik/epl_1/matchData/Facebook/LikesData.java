package epl.app.net.basalamah.malik.epl_1.MatchData.Facebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik on 08-Nov-15.
 */
public class LikesData {


        @SerializedName("data")
        @Expose
        private List<Likes> data = new ArrayList<Likes>();

        /**
         *
         * @return
         * The data
         */
        public List<Likes> getData() {
                return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<Likes> data) {
                this.data = data;
        }
}
