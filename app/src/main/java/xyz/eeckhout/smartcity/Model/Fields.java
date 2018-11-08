
package xyz.eeckhout.smartcity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Fields implements Serializable
{

    @SerializedName("post_code")
    @Expose
    private String postCode;
    @SerializedName("places")
    @Expose
    private Integer places;
    @SerializedName("plsy_descri")
    @Expose
    private String plsyDescri;
    @SerializedName("plsy_fonction")
    @Expose
    private String plsyFonction;
    @SerializedName("geo_point_2d")
    @Expose
    private List<Float> geoPoint2d = null;
    @SerializedName("acom_nom_m")
    @Expose
    private String acomNomM;
    @SerializedName("ouverture")
    @Expose
    private String ouverture;
    @SerializedName("geo_shape")
    @Expose
    private GeoShape geoShape;
    @SerializedName("plsy_tel")
    @Expose
    private String plsyTel;
    @SerializedName("rue_nom")
    @Expose
    private String rueNom;
    @SerializedName("adr_nopol")
    @Expose
    private String adrNopol;
    private final static long serialVersionUID = -6904665243424020517L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Fields() {
    }

    /**
     * 
     * @param acomNomM
     * @param geoShape
     * @param plsyDescri
     * @param plsyTel
     * @param postCode
     * @param plsyFonction
     * @param ouverture
     * @param geoPoint2d
     * @param places
     * @param adrNopol
     * @param rueNom
     */
    public Fields(String postCode, Integer places, String plsyDescri, String plsyFonction, List<Float> geoPoint2d, String acomNomM, String ouverture, GeoShape geoShape, String plsyTel, String rueNom, String adrNopol) {
        super();
        this.postCode = postCode;
        this.places = places;
        this.plsyDescri = plsyDescri;
        this.plsyFonction = plsyFonction;
        this.geoPoint2d = geoPoint2d;
        this.acomNomM = acomNomM;
        this.ouverture = ouverture;
        this.geoShape = geoShape;
        this.plsyTel = plsyTel;
        this.rueNom = rueNom;
        this.adrNopol = adrNopol;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String getPlsyDescri() {
        return plsyDescri;
    }

    public void setPlsyDescri(String plsyDescri) {
        this.plsyDescri = plsyDescri;
    }

    public String getPlsyFonction() {
        return plsyFonction;
    }

    public void setPlsyFonction(String plsyFonction) {
        this.plsyFonction = plsyFonction;
    }

    public List<Float> getGeoPoint2d() {
        return geoPoint2d;
    }

    public void setGeoPoint2d(List<Float> geoPoint2d) {
        this.geoPoint2d = geoPoint2d;
    }

    public String getAcomNomM() {
        return acomNomM;
    }

    public void setAcomNomM(String acomNomM) {
        this.acomNomM = acomNomM;
    }

    public String getOuverture() {
        return ouverture;
    }

    public void setOuverture(String ouverture) {
        this.ouverture = ouverture;
    }

    public GeoShape getGeoShape() {
        return geoShape;
    }

    public void setGeoShape(GeoShape geoShape) {
        this.geoShape = geoShape;
    }

    public String getPlsyTel() {
        return plsyTel;
    }

    public void setPlsyTel(String plsyTel) {
        this.plsyTel = plsyTel;
    }

    public String getRueNom() {
        return rueNom;
    }

    public void setRueNom(String rueNom) {
        this.rueNom = rueNom;
    }

    public String getAdrNopol() {
        return adrNopol;
    }

    public void setAdrNopol(String adrNopol) {
        this.adrNopol = adrNopol;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "postCode='" + postCode + '\'' +
                ", places=" + places +
                ", plsyDescri='" + plsyDescri + '\'' +
                ", plsyFonction='" + plsyFonction + '\'' +
                ", geoPoint2d=" + geoPoint2d +
                ", acomNomM='" + acomNomM + '\'' +
                ", ouverture='" + ouverture + '\'' +
                ", geoShape=" + geoShape +
                ", plsyTel='" + plsyTel + '\'' +
                ", rueNom='" + rueNom + '\'' +
                ", adrNopol='" + adrNopol + '\'' +
                '}';
    }
}
