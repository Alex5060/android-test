
package xyz.eeckhout.smartcity.model.googleMapsDirection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DurationInTraffic {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private int value;

    /**
     * No args constructor for use in serialization
     */
    public DurationInTraffic() {
    }

    /**
     * @param text
     * @param value
     */
    public DurationInTraffic(String text, int value) {
        super();
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
