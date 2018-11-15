package xyz.eeckhout.smartcity.model.jcdecaux;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JCDecauxStation implements Serializable
{
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("contract_name")
    @Expose
    private String contractName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("position")
    @Expose
    private Position position;
    @SerializedName("banking")
    @Expose
    private Boolean banking;
    @SerializedName("bonus")
    @Expose
    private Boolean bonus;
    @SerializedName("bike_stands")
    @Expose
    private Integer bikeStands;
    @SerializedName("available_bike_stands")
    @Expose
    private Integer availableBikeStands;
    @SerializedName("available_bikes")
    @Expose
    private Integer availableBikes;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("last_update")
    @Expose
    private Double lastUpdate;
    private final static long serialVersionUID = 3961762798081847958L;

    /**
     * No args constructor for use in serialization
     *
     */
    public JCDecauxStation() {
    }

    /**
     *
     * @param position
     * @param bikeStands
     * @param status
     * @param address
     * @param lastUpdate
     * @param name
     * @param availableBikeStands
     * @param banking
     * @param bonus
     * @param number
     * @param contractName
     * @param availableBikes
     */
    public JCDecauxStation(Integer number, String contractName, String name, String address, Position position, Boolean banking, Boolean bonus, Integer bikeStands, Integer availableBikeStands, Integer availableBikes, String status, Double lastUpdate) {
        super();
        this.number = number;
        this.contractName = contractName;
        this.name = name;
        this.address = address;
        this.position = position;
        this.banking = banking;
        this.bonus = bonus;
        this.bikeStands = bikeStands;
        this.availableBikeStands = availableBikeStands;
        this.availableBikes = availableBikes;
        this.status = status;
        this.lastUpdate = lastUpdate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Boolean getBanking() {
        return banking;
    }

    public void setBanking(Boolean banking) {
        this.banking = banking;
    }

    public Boolean getBonus() {
        return bonus;
    }

    public void setBonus(Boolean bonus) {
        this.bonus = bonus;
    }

    public Integer getBikeStands() {
        return bikeStands;
    }

    public void setBikeStands(Integer bikeStands) {
        this.bikeStands = bikeStands;
    }

    public Integer getAvailableBikeStands() {
        return availableBikeStands;
    }

    public void setAvailableBikeStands(Integer availableBikeStands) {
        this.availableBikeStands = availableBikeStands;
    }

    public Integer getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(Integer availableBikes) {
        this.availableBikes = availableBikes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Double lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "JCDecauxStation{" +
                "number=" + number +
                ", contractName='" + contractName + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", position=" + position +
                ", banking=" + banking +
                ", bonus=" + bonus +
                ", bikeStands=" + bikeStands +
                ", availableBikeStands=" + availableBikeStands +
                ", availableBikes=" + availableBikes +
                ", status='" + status + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
