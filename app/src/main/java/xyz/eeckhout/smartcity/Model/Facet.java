package xyz.eeckhout.smartcity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Facet implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("state")
    @Expose
    private String state;
    private final static long serialVersionUID = -6908481287948012207L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Facet() {
    }

    /**
     * 
     * @param count
     * @param name
     * @param state
     * @param path
     */
    public Facet(String name, String path, Integer count, String state) {
        super();
        this.name = name;
        this.path = path;
        this.count = count;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Facet{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", count=" + count +
                ", state='" + state + '\'' +
                '}';
    }
}
