
package xyz.eeckhout.smartcity.model.graphhopper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Path {

    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("transfers")
    @Expose
    private Integer transfers;
    @SerializedName("points_encoded")
    @Expose
    private Boolean pointsEncoded;
    @SerializedName("bbox")
    @Expose
    private List<Double> bbox = null;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("instructions")
    @Expose
    private List<Instruction> instructions = null;
    @SerializedName("legs")
    @Expose
    private List<Object> legs = null;
    @SerializedName("details")
    @Expose
    private Details details;
    @SerializedName("ascend")
    @Expose
    private Double ascend;
    @SerializedName("descend")
    @Expose
    private Double descend;
    @SerializedName("snapped_waypoints")
    @Expose
    private String snappedWaypoints;

    /**
     * No args constructor for use in serialization
     */
    public Path() {
    }

    /**
     * @param snappedWaypoints
     * @param transfers
     * @param time
     * @param distance
     * @param weight
     * @param details
     * @param instructions
     * @param bbox
     * @param pointsEncoded
     * @param legs
     * @param descend
     * @param points
     * @param ascend
     */
    public Path(Double distance, Double weight, Integer time, Integer transfers, Boolean pointsEncoded, List<Double> bbox, String points, List<Instruction> instructions, List<Object> legs, Details details, Double ascend, Double descend, String snappedWaypoints) {
        super();
        this.distance = distance;
        this.weight = weight;
        this.time = time;
        this.transfers = transfers;
        this.pointsEncoded = pointsEncoded;
        this.bbox = bbox;
        this.points = points;
        this.instructions = instructions;
        this.legs = legs;
        this.details = details;
        this.ascend = ascend;
        this.descend = descend;
        this.snappedWaypoints = snappedWaypoints;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTransfers() {
        return transfers;
    }

    public void setTransfers(Integer transfers) {
        this.transfers = transfers;
    }

    public Boolean getPointsEncoded() {
        return pointsEncoded;
    }

    public void setPointsEncoded(Boolean pointsEncoded) {
        this.pointsEncoded = pointsEncoded;
    }

    public List<Double> getBbox() {
        return bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public List<Object> getLegs() {
        return legs;
    }

    public void setLegs(List<Object> legs) {
        this.legs = legs;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Double getAscend() {
        return ascend;
    }

    public void setAscend(Double ascend) {
        this.ascend = ascend;
    }

    public Double getDescend() {
        return descend;
    }

    public void setDescend(Double descend) {
        this.descend = descend;
    }

    public String getSnappedWaypoints() {
        return snappedWaypoints;
    }

    public void setSnappedWaypoints(String snappedWaypoints) {
        this.snappedWaypoints = snappedWaypoints;
    }

}
