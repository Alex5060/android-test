
package xyz.eeckhout.smartcity.model.mapbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Intersection {

    @SerializedName("out")
    @Expose
    private Integer out;
    @SerializedName("entry")
    @Expose
    private List<Boolean> entry = null;
    @SerializedName("bearings")
    @Expose
    private List<Integer> bearings = null;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;
    @SerializedName("in")
    @Expose
    private Integer in;

    /**
     * No args constructor for use in serialization
     */
    public Intersection() {
    }

    /**
     * @param location
     * @param bearings
     * @param entry
     * @param in
     * @param out
     */
    public Intersection(Integer out, List<Boolean> entry, List<Integer> bearings, List<Double> location, Integer in) {
        super();
        this.out = out;
        this.entry = entry;
        this.bearings = bearings;
        this.location = location;
        this.in = in;
    }

    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }

    public List<Boolean> getEntry() {
        return entry;
    }

    public void setEntry(List<Boolean> entry) {
        this.entry = entry;
    }

    public List<Integer> getBearings() {
        return bearings;
    }

    public void setBearings(List<Integer> bearings) {
        this.bearings = bearings;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Integer getIn() {
        return in;
    }

    public void setIn(Integer in) {
        this.in = in;
    }

}
