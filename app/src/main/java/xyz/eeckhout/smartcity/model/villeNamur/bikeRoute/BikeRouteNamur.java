
package xyz.eeckhout.smartcity.model.villeNamur.bikeRoute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BikeRouteNamur implements Serializable
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
    private final static long serialVersionUID = 8891545412935296308L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BikeRouteNamur() {
    }

    /**
     * 
     * @param nhits
     * @param parameters
     * @param records
     * @param facetGroups
     */
    public BikeRouteNamur(Integer nhits, Parameters parameters, List<Record> records, List<FacetGroup> facetGroups) {
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

    @Override
    public String toString() {
        return "BikeRouteNamur{" +
                "nhits=" + nhits +
                ", parameters=" + parameters +
                ", records=" + records +
                ", facetGroups=" + facetGroups +
                '}';
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

}