package epl.app.net.basalamah.malik.epl_1.MatchData.Facebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Malik on 08-Nov-15.
 */
public class FacebookData {
    @SerializedName("photos")
    @Expose
    private FacebookAlbum photos;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     * The photos
     */
    public FacebookAlbum getPhotos() {
        return photos;
    }

    /**
     *
     * @param photos
     * The photos
     */
    public void setPhotos(FacebookAlbum photos) {
        this.photos = photos;
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
