
package xyz.eeckhout.smartcity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ParkingAuto implements Serializable
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
    @SerializedName("facet_groups")
    @Expose
    private List<FacetGroup> facetGroups = null;
    private final static long serialVersionUID = 849184003397715162L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ParkingAuto() {
    }

    /**
     * 
     * @param nhits
     * @param parameters
     * @param records
     * @param facetGroups
     */
    public ParkingAuto(Integer nhits, Parameters parameters, List<Record> records, List<FacetGroup> facetGroups) {
        super();
        this.nhits = nhits;
        this.parameters = parameters;
        this.records = records;
        this.facetGroups = facetGroups;
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

    public List<FacetGroup> getFacetGroups() {
        return facetGroups;
    }

    public void setFacetGroups(List<FacetGroup> facetGroups) {
        this.facetGroups = facetGroups;
    }

    @Override
    public String toString() {
        return "ParkingAuto{" +
                "nhits=" + nhits +
                ", parameters=" + parameters +
                ", records=" + records +
                ", facetGroups=" + facetGroups +
                '}';
    }
}
