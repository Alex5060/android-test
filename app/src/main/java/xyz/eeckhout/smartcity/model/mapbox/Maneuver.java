
package xyz.eeckhout.smartcity.model.mapbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Maneuver {

    @SerializedName("bearing_after")
    @Expose
    private Integer bearingAfter;
    @SerializedName("bearing_before")
    @Expose
    private Integer bearingBefore;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("instruction")
    @Expose
    private String instruction;

    /**
     * No args constructor for use in serialization
     */
    public Maneuver() {
    }

    /**
     * @param bearingBefore
     * @param location
     * @param bearingAfter
     * @param instruction
     * @param type
     */
    public Maneuver(Integer bearingAfter, Integer bearingBefore, List<Double> location, String type, String instruction) {
        super();
        this.bearingAfter = bearingAfter;
        this.bearingBefore = bearingBefore;
        this.location = location;
        this.type = type;
        this.instruction = instruction;
    }

    public Integer getBearingAfter() {
        return bearingAfter;
    }

    public void setBearingAfter(Integer bearingAfter) {
        this.bearingAfter = bearingAfter;
    }

    public Integer getBearingBefore() {
        return bearingBefore;
    }

    public void setBearingBefore(Integer bearingBefore) {
        this.bearingBefore = bearingBefore;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

}
