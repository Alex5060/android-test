
package xyz.eeckhout.smartcity.model.mapbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Waypoint {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;

    /**
     * No args constructor for use in serialization
     */
    public Waypoint() {
    }

    /**
     * @param location
     * @param name
     */
    public Waypoint(String name, List<Double> location) {
        super();
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

}
