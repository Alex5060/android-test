
package xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVelo;

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
    private final static long serialVersionUID = 6036565725064071909L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Parameters() {
    }

    /**
     * 
     * @param timezone
     * @param dataset
     * @param format
     * @param rows
     */
    public Parameters(List<String> dataset, String timezone, Integer rows, String format) {
        super();
        this.dataset = dataset;
        this.timezone = timezone;
        this.rows = rows;
        this.format = format;
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

    @Override
    public String toString() {
        return "Parameters{" +
                "dataset=" + dataset +
                ", timezone='" + timezone + '\'' +
                ", rows=" + rows +
                ", format='" + format + '\'' +
                '}';
    }
}
