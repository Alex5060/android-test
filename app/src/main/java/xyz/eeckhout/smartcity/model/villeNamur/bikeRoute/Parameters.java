
package xyz.eeckhout.smartcity.model.villeNamur.bikeRoute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Parameters implements Serializable
{

    @SerializedName("dataset")
    @Expose
    private List<String> dataset = null;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("rows")
    @Expose
    private Integer rows;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("facet")
    @Expose
    private List<String> facet = null;
    private final static long serialVersionUID = 8726846473049128782L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Parameters() {
    }

    /**
     * 
     * @param facet
     * @param timezone
     * @param dataset
     * @param format
     * @param rows
     */
    public Parameters(List<String> dataset, String timezone, Integer rows, String format, List<String> facet) {
        super();
        this.dataset = dataset;
        this.timezone = timezone;
        this.rows = rows;
        this.format = format;
        this.facet = facet;
    }

    public List<String> getDataset() {
        return dataset;
    }

    public void setDataset(List<String> dataset) {
        this.dataset = dataset;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<String> getFacet() {
        return facet;
    }

    public void setFacet(List<String> facet) {
        this.facet = facet;
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "dataset=" + dataset +
                ", timezone='" + timezone + '\'' +
                ", rows=" + rows +
                ", format='" + format + '\'' +
                ", facet=" + facet +
                '}';
    }
}
