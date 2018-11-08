
package xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import xyz.eeckhout.smartcity.Model.VilleNamur.GeoShape;

public class Fields implements Serializable
{

    @SerializedName("nom_station")
    @Expose
    private String nomStation;
    @SerializedName("matiere")
    @Expose
    private String matiere;
    @SerializedName("nbre_arceaux")
    @Expose
    private String nbreArceaux;
    @SerializedName("geo_point_2d")
    @Expose
    private List<Float> geoPoint2d = null;
    @SerializedName("type_stationnement")
    @Expose
    private String typeStationnement;
    @SerializedName("couvert")
    @Expose
    private String couvert;
    @SerializedName("securise")
    @Expose
    private String securise;
    @SerializedName("geo_shape")
    @Expose
    private GeoShape geoShape;
    @SerializedName("localite")
    @Expose
    private String localite;
    @SerializedName("securise_type")
    @Expose
    private String securiseType;
    @SerializedName("couvert_type")
    @Expose
    private String couvertType;
    private final static long serialVersionUID = -3769664893465555439L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Fields() {
    }

    /**
     * 
     * @param geoShape
     * @param securise
     * @param typeStationnement
     * @param securiseType
     * @param couvertType
     * @param nbreArceaux
     * @param matiere
     * @param localite
     * @param couvert
     * @param geoPoint2d
     * @param nomStation
     */
    public Fields(String nomStation, String matiere, String nbreArceaux, List<Float> geoPoint2d, String typeStationnement, String couvert, String securise, GeoShape geoShape, String localite, String securiseType, String couvertType) {
        super();
        this.nomStation = nomStation;
        this.matiere = matiere;
        this.nbreArceaux = nbreArceaux;
        this.geoPoint2d = geoPoint2d;
        this.typeStationnement = typeStationnement;
        this.couvert = couvert;
        this.securise = securise;
        this.geoShape = geoShape;
        this.localite = localite;
        this.securiseType = securiseType;
        this.couvertType = couvertType;
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNbreArceaux() {
        return nbreArceaux;
    }

    public void setNbreArceaux(String nbreArceaux) {
        this.nbreArceaux = nbreArceaux;
    }

    public List<Float> getGeoPoint2d() {
        return geoPoint2d;
    }

    public void setGeoPoint2d(List<Float> geoPoint2d) {
        this.geoPoint2d = geoPoint2d;
    }

    public String getTypeStationnement() {
        return typeStationnement;
    }

    public void setTypeStationnement(String typeStationnement) {
        this.typeStationnement = typeStationnement;
    }

    public String getCouvert() {
        return couvert;
    }

    public void setCouvert(String couvert) {
        this.couvert = couvert;
    }

    public String getSecurise() {
        return securise;
    }

    public void setSecurise(String securise) {
        this.securise = securise;
    }

    public GeoShape getGeoShape() {
        return geoShape;
    }

    public void setGeoShape(GeoShape geoShape) {
        this.geoShape = geoShape;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getSecuriseType() {
        return securiseType;
    }

    public void setSecuriseType(String securiseType) {
        this.securiseType = securiseType;
    }

    public String getCouvertType() {
        return couvertType;
    }

    public void setCouvertType(String couvertType) {
        this.couvertType = couvertType;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "nomStation='" + nomStation + '\'' +
                ", matiere='" + matiere + '\'' +
                ", nbreArceaux='" + nbreArceaux + '\'' +
                ", geoPoint2d=" + geoPoint2d +
                ", typeStationnement='" + typeStationnement + '\'' +
                ", couvert='" + couvert + '\'' +
                ", securise='" + securise + '\'' +
                ", geoShape=" + geoShape +
                ", localite='" + localite + '\'' +
                ", securiseType='" + securiseType + '\'' +
                ", couvertType='" + couvertType + '\'' +
                '}';
    }
}
