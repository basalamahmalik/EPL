package epl.app.net.basalamah.malik.epl_1.MatchData.Facebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Malik on 08-Nov-15.
 */
public class Likes {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("id")
        @Expose
        private String id;

        /**
         *
         * @return
         * The name
         */
        public String getName() {
                return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
                this.name = name;
        }

        /**
         *
         * @return
         * The id
         */
        public String getId() {
                return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
                this.id = id;
        }
}
