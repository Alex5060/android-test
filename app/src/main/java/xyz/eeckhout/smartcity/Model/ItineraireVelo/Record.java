
package xyz.eeckhout.smartcity.Model.ItineraireVelo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record implements Serializable
{

    @SerializedName("datasetid")
    @Expose
    private String datasetid;
    @SerializedName("recordid")
    @Expose
    private String recordid;
    @SerializedName("fields")
    @Expose
    private Fields fields;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("record_timestamp")
    @Expose
    private String recordTimestamp;
    private final static long serialVersionUID = -2161376268193978110L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Record() {
    }

    /**
     * 
     * @param datasetid
     * @param recordTimestamp
     * @param recordid
     * @param geometry
     * @param fields
     */
    public Record(String datasetid, String recordid, Fields fields, Geometry geometry, String recordTimestamp) {
        super();
        this.datasetid = datasetid;
        this.recordid = recordid;
        this.fields = fields;
        this.geometry = geometry;
        this.recordTimestamp = recordTimestamp;
    }

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(String recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    @Override
    public String toString() {
        return "Record{" +
                "datasetid='" + datasetid + '\'' +
                ", recordid='" + recordid + '\'' +
                ", fields=" + fields +
                ", geometry=" + geometry +
                ", recordTimestamp='" + recordTimestamp + '\'' +
                '}';
    }
}
