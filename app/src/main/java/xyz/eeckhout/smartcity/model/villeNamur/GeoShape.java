
package xyz.eeckhout.smartcity.model.villeNamur;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GeoShape implements Serializable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coordinates")
    @Expose
    private List<Float> coordinates = null;
    private final static long serialVersionUID = 6472639976848482119L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GeoShape() {
    }

    /**
     * 
     * @param type
     * @param coordinates
     */
    public GeoShape(String type, List<Float> coordinates) {
        super();
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Float> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Float> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "GeoShape{" +
                "type='" + type + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
