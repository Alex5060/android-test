
package xyz.eeckhout.smartcity.model.mapbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mapbox {

    @SerializedName("routes")
    @Expose
    private List<Route> routes = null;
    @SerializedName("waypoints")
    @Expose
    private List<Waypoint> waypoints = null;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("uuid")
    @Expose
    private String uuid;

    /**
     * No args constructor for use in serialization
     */
    public Mapbox() {
    }

    /**
     * @param routes
     * @param uuid
     * @param code
     * @param waypoints
     */
    public Mapbox(List<Route> routes, List<Waypoint> waypoints, String code, String uuid) {
        super();
        this.routes = routes;
        this.waypoints = waypoints;
        this.code = code;
        this.uuid = uuid;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
