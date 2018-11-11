
package xyz.eeckhout.smartcity.model.villeNamur.bikeRoute;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//public class GeoShape implements Serializable
//{
//
//    @SerializedName("type")
//    @Expose
//    private String type;
//    @SerializedName("coordinates")
//    @Expose
//    private List<ArrayList<Double>> coordinates = null;
//    private final static long serialVersionUID = 4903036871719844936L;
//
//    /**
//     * No args constructor for use in serialization
//     *
//     */
//    public GeoShape() {
//    }
//
//    @Override
//    public String toString() {
//        return "GeoShape{" +
//                "type='" + type + '\'' +
//                '}';
//    }
//
//    /**
//     *
//     * @param type
//     *
//     */
//    public GeoShape(String type, List<ArrayList<Double>> coordinates) {
//        super();
//        this.type = type;
//        this.coordinates = coordinates;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public List<ArrayList<Double>> getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(List<ArrayList<Double>> coordinates) {
//        this.coordinates = coordinates;
//    }
//
//}

public class GeoShape implements Serializable
{
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coordinates")
    @Expose
    private List<List<Double>> coordinates = null;
    private final static long serialVersionUID = -1304369022062517580L;

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
    public GeoShape(String type, List<List<Double>> coordinates) {
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

    public GeoShape withType(String type) {
        this.type = type;
        return this;
    }

    public ArrayList<LatLng> getLatLng(){
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for(List<Double> list : coordinates){
            latLngs.add(new LatLng(list.get(1), list.get(0)));
        }
        return latLngs;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public GeoShape withCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).append("type", type).append("coordinates", coordinates).toString();
//    }

}