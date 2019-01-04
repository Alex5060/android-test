/*
 * My API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package xyz.eeckhout.smartcity.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * ServiceGetDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-03T20:36:15.687+01:00")
public class ServiceGetDTO {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("recordid")
  private String recordid = null;

  @SerializedName("datasetid")
  private String datasetid = null;

  @SerializedName("state")
  private String state = null;

  @SerializedName("creator")
  private String creator = null;

  @SerializedName("longitude")
  private Double longitude = null;

  @SerializedName("latitude")
  private Double latitude = null;

  @SerializedName("serviceName")
  private String serviceName = null;

  @SerializedName("categoryId")
  private Integer categoryId = null;

  @SerializedName("facultativeValue")
  private String facultativeValue = null;

  public ServiceGetDTO id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ServiceGetDTO recordid(String recordid) {
    this.recordid = recordid;
    return this;
  }

   /**
   * Get recordid
   * @return recordid
  **/
  @ApiModelProperty(value = "")
  public String getRecordid() {
    return recordid;
  }

  public void setRecordid(String recordid) {
    this.recordid = recordid;
  }

  public ServiceGetDTO datasetid(String datasetid) {
    this.datasetid = datasetid;
    return this;
  }

   /**
   * Get datasetid
   * @return datasetid
  **/
  @ApiModelProperty(value = "")
  public String getDatasetid() {
    return datasetid;
  }

  public void setDatasetid(String datasetid) {
    this.datasetid = datasetid;
  }

  public ServiceGetDTO state(String state) {
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @ApiModelProperty(value = "")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public ServiceGetDTO creator(String creator) {
    this.creator = creator;
    return this;
  }

   /**
   * Get creator
   * @return creator
  **/
  @ApiModelProperty(value = "")
  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public ServiceGetDTO longitude(Double longitude) {
    this.longitude = longitude;
    return this;
  }

   /**
   * Get longitude
   * @return longitude
  **/
  @ApiModelProperty(required = true, value = "")
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public ServiceGetDTO latitude(Double latitude) {
    this.latitude = latitude;
    return this;
  }

   /**
   * Get latitude
   * @return latitude
  **/
  @ApiModelProperty(required = true, value = "")
  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public ServiceGetDTO serviceName(String serviceName) {
    this.serviceName = serviceName;
    return this;
  }

   /**
   * Get serviceName
   * @return serviceName
  **/
  @ApiModelProperty(required = true, value = "")
  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public ServiceGetDTO categoryId(Integer categoryId) {
    this.categoryId = categoryId;
    return this;
  }

   /**
   * Get categoryId
   * @return categoryId
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public ServiceGetDTO facultativeValue(String facultativeValue) {
    this.facultativeValue = facultativeValue;
    return this;
  }

   /**
   * Get facultativeValue
   * @return facultativeValue
  **/
  @ApiModelProperty(value = "")
  public String getFacultativeValue() {
    return facultativeValue;
  }

  public void setFacultativeValue(String facultativeValue) {
    this.facultativeValue = facultativeValue;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceGetDTO serviceGetDTO = (ServiceGetDTO) o;
    return Objects.equals(this.id, serviceGetDTO.id) &&
        Objects.equals(this.recordid, serviceGetDTO.recordid) &&
        Objects.equals(this.datasetid, serviceGetDTO.datasetid) &&
        Objects.equals(this.state, serviceGetDTO.state) &&
        Objects.equals(this.creator, serviceGetDTO.creator) &&
        Objects.equals(this.longitude, serviceGetDTO.longitude) &&
        Objects.equals(this.latitude, serviceGetDTO.latitude) &&
        Objects.equals(this.serviceName, serviceGetDTO.serviceName) &&
        Objects.equals(this.categoryId, serviceGetDTO.categoryId) &&
        Objects.equals(this.facultativeValue, serviceGetDTO.facultativeValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, recordid, datasetid, state, creator, longitude, latitude, serviceName, categoryId, facultativeValue);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceGetDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    recordid: ").append(toIndentedString(recordid)).append("\n");
    sb.append("    datasetid: ").append(toIndentedString(datasetid)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
    sb.append("    categoryId: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    facultativeValue: ").append(toIndentedString(facultativeValue)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

