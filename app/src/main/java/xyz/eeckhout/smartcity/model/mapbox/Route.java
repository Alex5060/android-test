
package xyz.eeckhout.smartcity.model.mapbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

    @SerializedName("geometry")
    @Expose
    private String geometry;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;
    @SerializedName("weight_name")
    @Expose
    private String weightName;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("distance")
    @Expose
    private Integer distance;

    /**
     * No args constructor for use in serialization
     */
    public Route() {
    }

    /**
     * @param distance
     * @param duration
     * @param weightName
     * @param weight
     * @param legs
     * @param geometry
     */
    public Route(String geometry, List<Leg> legs, String weightName, Integer weight, Integer duration, Integer distance) {
        super();
        this.geometry = geometry;
        this.legs = legs;
        this.weightName = weightName;
        this.weight = weight;
        this.duration = duration;
        this.distance = distance;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

}
