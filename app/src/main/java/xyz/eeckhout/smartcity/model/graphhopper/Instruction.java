
package xyz.eeckhout.smartcity.model.graphhopper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Instruction {

    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("heading")
    @Expose
    private Double heading;
    @SerializedName("sign")
    @Expose
    private Integer sign;
    @SerializedName("interval")
    @Expose
    private List<Integer> interval = null;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("street_name")
    @Expose
    private String streetName;
    @SerializedName("exit_number")
    @Expose
    private Integer exitNumber;
    @SerializedName("exited")
    @Expose
    private Boolean exited;
    @SerializedName("turn_angle")
    @Expose
    private Double turnAngle;
    @SerializedName("last_heading")
    @Expose
    private Double lastHeading;

    /**
     * No args constructor for use in serialization
     */
    public Instruction() {
    }

    /**
     * @param sign
     * @param time
     * @param text
     * @param distance
     * @param interval
     * @param lastHeading
     * @param turnAngle
     * @param exitNumber
     * @param streetName
     * @param exited
     * @param heading
     */
    public Instruction(Double distance, Double heading, Integer sign, List<Integer> interval, String text, Integer time, String streetName, Integer exitNumber, Boolean exited, Double turnAngle, Double lastHeading) {
        super();
        this.distance = distance;
        this.heading = heading;
        this.sign = sign;
        this.interval = interval;
        this.text = text;
        this.time = time;
        this.streetName = streetName;
        this.exitNumber = exitNumber;
        this.exited = exited;
        this.turnAngle = turnAngle;
        this.lastHeading = lastHeading;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public List<Integer> getInterval() {
        return interval;
    }

    public void setInterval(List<Integer> interval) {
        this.interval = interval;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getExitNumber() {
        return exitNumber;
    }

    public void setExitNumber(Integer exitNumber) {
        this.exitNumber = exitNumber;
    }

    public Boolean getExited() {
        return exited;
    }

    public void setExited(Boolean exited) {
        this.exited = exited;
    }

    public Double getTurnAngle() {
        return turnAngle;
    }

    public void setTurnAngle(Double turnAngle) {
        this.turnAngle = turnAngle;
    }

    public Double getLastHeading() {
        return lastHeading;
    }

    public void setLastHeading(Double lastHeading) {
        this.lastHeading = lastHeading;
    }

}
