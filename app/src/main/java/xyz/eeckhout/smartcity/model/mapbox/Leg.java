
package xyz.eeckhout.smartcity.model.mapbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Leg {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("distance")
    @Expose
    private Integer distance;

    /**
     * No args constructor for use in serialization
     */
    public Leg() {
    }

    /**
     * @param summary
     * @param distance
     * @param duration
     * @param weight
     * @param steps
     */
    public Leg(String summary, Integer weight, Integer duration, List<Step> steps, Integer distance) {
        super();
        this.summary = summary;
        this.weight = weight;
        this.duration = duration;
        this.steps = steps;
        this.distance = distance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

}
