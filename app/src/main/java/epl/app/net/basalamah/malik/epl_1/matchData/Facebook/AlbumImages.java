package epl.app.net.basalamah.malik.epl_1.MatchData.Facebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Malik on 08-Nov-15.
 */
public class AlbumImages {

    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("width")
    @Expose
    private Integer width;

    /**
     *
     * @return
     * The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }
    }
