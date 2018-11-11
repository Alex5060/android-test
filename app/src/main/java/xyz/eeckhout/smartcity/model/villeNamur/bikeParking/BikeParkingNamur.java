
package xyz.eeckhout.smartcity.model.villeNamur.bikeParking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BikeParkingNamur implements Serializable
{

    @SerializedName("nhits")
    @Expose
    private Integer nhits;
    @SerializedName("parameters")
    @Expose
    private Parameters parameters;
    @SerializedName("records")
    @Expose
    private List<Record> records = null;
    private final static long serialVersionUID = 7386561069958669794L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BikeParkingNamur() {
    }

    @Override
    public String toString() {
        return "BikeParkingNamur{" +
                "nhits=" + nhits +
                ", parameters=" + parameters +
                ", records=" + records +
                '}';
    }

    /**
     * 
     * @param nhits
     * @param parameters
     * @param records
     */
    public BikeParkingNamur(Integer nhits, Parameters parameters, List<Record> records) {
        super();
        this.nhits = nhits;
        this.parameters = parameters;
        this.records = records;
    }

    public Integer getNhits() {
        return nhits;
    }

    public void setNhits(Integer nhits) {
        this.nhits = nhits;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
