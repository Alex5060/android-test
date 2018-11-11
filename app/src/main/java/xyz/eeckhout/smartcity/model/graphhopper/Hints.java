
package xyz.eeckhout.smartcity.model.graphhopper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hints {

    @SerializedName("visited_nodes.average")
    @Expose
    private String visitedNodesAverage;
    @SerializedName("visited_nodes.sum")
    @Expose
    private String visitedNodesSum;

    /**
     * No args constructor for use in serialization
     */
    public Hints() {
    }

    /**
     * @param visitedNodesSum
     * @param visitedNodesAverage
     */
    public Hints(String visitedNodesAverage, String visitedNodesSum) {
        super();
        this.visitedNodesAverage = visitedNodesAverage;
        this.visitedNodesSum = visitedNodesSum;
    }

    public String getVisitedNodesAverage() {
        return visitedNodesAverage;
    }

    public void setVisitedNodesAverage(String visitedNodesAverage) {
        this.visitedNodesAverage = visitedNodesAverage;
    }

    public String getVisitedNodesSum() {
        return visitedNodesSum;
    }

    public void setVisitedNodesSum(String visitedNodesSum) {
        this.visitedNodesSum = visitedNodesSum;
    }

}
