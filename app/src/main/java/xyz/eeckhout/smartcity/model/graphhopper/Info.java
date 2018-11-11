
package xyz.eeckhout.smartcity.model.graphhopper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Info {

    @SerializedName("copyrights")
    @Expose
    private List<String> copyrights = null;
    @SerializedName("took")
    @Expose
    private Integer took;

    /**
     * No args constructor for use in serialization
     */
    public Info() {
    }

    /**
     * @param copyrights
     * @param took
     */
    public Info(List<String> copyrights, Integer took) {
        super();
        this.copyrights = copyrights;
        this.took = took;
    }

    public List<String> getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(List<String> copyrights) {
        this.copyrights = copyrights;
    }

    public Integer getTook() {
        return took;
    }

    public void setTook(Integer took) {
        this.took = took;
    }

}
