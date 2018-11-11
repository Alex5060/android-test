
package xyz.eeckhout.smartcity.model.mapbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Step {

    @SerializedName("intersections")
    @Expose
    private List<Intersection> intersections = null;
    @SerializedName("driving_side")
    @Expose
    private String drivingSide;
    @SerializedName("geometry")
    @Expose
    private String geometry;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("maneuver")
    @Expose
    private Maneuver maneuver;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("distance")
    @Expose
    private Integer distance;

    /**
     * No args constructor for use in serialization
     */
    public Step() {
    }

    /**
     * @param drivingSide
     * @param intersections
     * @param distance
     * @param duration
     * @param weight
     * @param name
     * @param maneuver
     * @param mode
     * @param geometry
     */
    public Step(List<Intersection> intersections, String drivingSide, String geometry, String mode, Maneuver maneuver, Integer weight, Integer duration, String name, Integer distance) {
        super();
        this.intersections = intersections;
        this.drivingSide = drivingSide;
        this.geometry = geometry;
        this.mode = mode;
        this.maneuver = maneuver;
        this.weight = weight;
        this.duration = duration;
        this.name = name;
        this.distance = distance;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    public String getDrivingSide() {
        return drivingSide;
    }

    public void setDrivingSide(String drivingSide) {
        this.drivingSide = drivingSide;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Maneuver getManeuver() {
        return maneuver;
    }

    public void setManeuver(Maneuver maneuver) {
        this.maneuver = maneuver;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

}
