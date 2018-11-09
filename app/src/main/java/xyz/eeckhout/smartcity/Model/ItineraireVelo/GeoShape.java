
package xyz.eeckhout.smartcity.Model.ItineraireVelo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoShape implements Serializable
{

    @SerializedName("type")
    @Expose
    private String type;
//    @SerializedName("coordinates")
//    @Expose
//    private List<List<Float>> coordinates = null;
    private final static long serialVersionUID = 4903036871719844936L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GeoShape() {
    }

    @Override
    public String toString() {
        return "GeoShape{" +
                "type='" + type + '\'' +
                '}';
    }

    /**
     * 
     * @param type
     *
     */
    public GeoShape(String type) {
        super();
        this.type = type;
        //this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public List<List<Float>> getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(List<List<Float>> coordinates) {
//        this.coordinates = coordinates;
//    }

}
