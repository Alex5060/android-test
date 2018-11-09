
package xyz.eeckhout.smartcity.Model.ItineraireVelo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields implements Serializable
{

    @SerializedName("iti_regional")
    @Expose
    private String itiRegional;
    @SerializedName("iti_communal")
    @Expose
    private String itiCommunal;
    @SerializedName("iti_code_reg")
    @Expose
    private String itiCodeReg;
    @SerializedName("categorie")
    @Expose
    private String categorie;
    @SerializedName("iti_code_eu")
    @Expose
    private String itiCodeEu;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("geo_point_2d")
    @Expose
    private List<Float> geoPoint2d = null;
    @SerializedName("iti_code_com")
    @Expose
    private String itiCodeCom;
    @SerializedName("geo_shape")
    @Expose
    private GeoShape geoShape;
    @SerializedName("iti_europeen")
    @Expose
    private String itiEuropeen;
    @SerializedName("id")
    @Expose
    private Integer id;
    private final static long serialVersionUID = -1044163352695154935L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Fields() {
    }

    /**
     * 
     * @param id
     * @param itiCommunal
     * @param geoShape
     * @param itiCodeReg
     * @param itiEuropeen
     * @param categorie
     * @param itiCodeEu
     * @param itiCodeCom
     * @param itiRegional
     * @param geoPoint2d
     * @param nom
     */
    public Fields(String itiRegional, String itiCommunal, String itiCodeReg, String categorie, String itiCodeEu, String nom, List<Float> geoPoint2d, String itiCodeCom, GeoShape geoShape, String itiEuropeen, Integer id) {
        super();
        this.itiRegional = itiRegional;
        this.itiCommunal = itiCommunal;
        this.itiCodeReg = itiCodeReg;
        this.categorie = categorie;
        this.itiCodeEu = itiCodeEu;
        this.nom = nom;
        this.geoPoint2d = geoPoint2d;
        this.itiCodeCom = itiCodeCom;
        this.geoShape = geoShape;
        this.itiEuropeen = itiEuropeen;
        this.id = id;
    }

    public String getItiRegional() {
        return itiRegional;
    }

    public void setItiRegional(String itiRegional) {
        this.itiRegional = itiRegional;
    }

    public String getItiCommunal() {
        return itiCommunal;
    }

    public void setItiCommunal(String itiCommunal) {
        this.itiCommunal = itiCommunal;
    }

    public String getItiCodeReg() {
        return itiCodeReg;
    }

    public void setItiCodeReg(String itiCodeReg) {
        this.itiCodeReg = itiCodeReg;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getItiCodeEu() {
        return itiCodeEu;
    }

    public void setItiCodeEu(String itiCodeEu) {
        this.itiCodeEu = itiCodeEu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Float> getGeoPoint2d() {
        return geoPoint2d;
    }

    public void setGeoPoint2d(List<Float> geoPoint2d) {
        this.geoPoint2d = geoPoint2d;
    }

    public String getItiCodeCom() {
        return itiCodeCom;
    }

    public void setItiCodeCom(String itiCodeCom) {
        this.itiCodeCom = itiCodeCom;
    }

    public GeoShape getGeoShape() {
        return geoShape;
    }

    public void setGeoShape(GeoShape geoShape) {
        this.geoShape = geoShape;
    }

    public String getItiEuropeen() {
        return itiEuropeen;
    }

    public void setItiEuropeen(String itiEuropeen) {
        this.itiEuropeen = itiEuropeen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "itiRegional='" + itiRegional + '\'' +
                ", itiCommunal='" + itiCommunal + '\'' +
                ", itiCodeReg='" + itiCodeReg + '\'' +
                ", categorie='" + categorie + '\'' +
                ", itiCodeEu='" + itiCodeEu + '\'' +
                ", nom='" + nom + '\'' +
                ", geoPoint2d=" + geoPoint2d +
                ", itiCodeCom='" + itiCodeCom + '\'' +
                ", geoShape=" + geoShape +
                ", itiEuropeen='" + itiEuropeen + '\'' +
                ", id=" + id +
                '}';
    }
}
