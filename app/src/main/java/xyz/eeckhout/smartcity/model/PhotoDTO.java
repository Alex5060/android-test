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

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * PhotoDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-06T14:36:30.645+01:00")
public class PhotoDTO {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("url")
  private String url = null;

  @SerializedName("dateInsert")
  private DateTime dateInsert = null;

  @SerializedName("isVisible")
  private Boolean isVisible = null;

  @SerializedName("photographId")
  private String photographId = null;

  @SerializedName("serviceId")
  private Integer serviceId = null;

  @SerializedName("rowVersion")
  private String rowVersion = null;

  @SerializedName("name")
  private String name = null;

  public PhotoDTO id(Integer id) {
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

  public PhotoDTO url(String url) {
    this.url = url;
    return this;
  }

   /**
   * Get url
   * @return url
  **/
  @ApiModelProperty(value = "")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public PhotoDTO dateInsert(DateTime dateInsert) {
    this.dateInsert = dateInsert;
    return this;
  }

   /**
   * Get dateInsert
   * @return dateInsert
  **/
  @ApiModelProperty(value = "")
  public DateTime getDateInsert() {
    return dateInsert;
  }

  public void setDateInsert(DateTime dateInsert) {
    this.dateInsert = dateInsert;
  }

  public PhotoDTO isVisible(Boolean isVisible) {
    this.isVisible = isVisible;
    return this;
  }

   /**
   * Get isVisible
   * @return isVisible
  **/
  @ApiModelProperty(value = "")
  public Boolean isIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public PhotoDTO photographId(String photographId) {
    this.photographId = photographId;
    return this;
  }

   /**
   * Get photographId
   * @return photographId
  **/
  @ApiModelProperty(value = "")
  public String getPhotographId() {
    return photographId;
  }

  public void setPhotographId(String photographId) {
    this.photographId = photographId;
  }

  public PhotoDTO serviceId(Integer serviceId) {
    this.serviceId = serviceId;
    return this;
  }

   /**
   * Get serviceId
   * @return serviceId
  **/
  @ApiModelProperty(value = "")
  public Integer getServiceId() {
    return serviceId;
  }

  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  public PhotoDTO rowVersion(String rowVersion) {
    this.rowVersion = rowVersion;
    return this;
  }

   /**
   * Get rowVersion
   * @return rowVersion
  **/
  @ApiModelProperty(value = "")
  public String getRowVersion() {
    return rowVersion;
  }

  public void setRowVersion(String rowVersion) {
    this.rowVersion = rowVersion;
  }

  public PhotoDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PhotoDTO photoDTO = (PhotoDTO) o;
    return Objects.equals(this.id, photoDTO.id) &&
        Objects.equals(this.url, photoDTO.url) &&
        Objects.equals(this.dateInsert, photoDTO.dateInsert) &&
        Objects.equals(this.isVisible, photoDTO.isVisible) &&
        Objects.equals(this.photographId, photoDTO.photographId) &&
        Objects.equals(this.serviceId, photoDTO.serviceId) &&
        Objects.equals(this.rowVersion, photoDTO.rowVersion) &&
        Objects.equals(this.name, photoDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, dateInsert, isVisible, photographId, serviceId, rowVersion, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PhotoDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    dateInsert: ").append(toIndentedString(dateInsert)).append("\n");
    sb.append("    isVisible: ").append(toIndentedString(isVisible)).append("\n");
    sb.append("    photographId: ").append(toIndentedString(photographId)).append("\n");
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    rowVersion: ").append(toIndentedString(rowVersion)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
