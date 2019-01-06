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
 * ServiceLikeDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-06T14:36:30.645+01:00")
public class ServiceLikeDTO {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("score")
  private Double score = null;

  @SerializedName("dateInsert")
  private DateTime dateInsert = null;

  @SerializedName("userId")
  private String userId = null;

  @SerializedName("serviceId")
  private Integer serviceId = null;

  @SerializedName("rowVersion")
  private String rowVersion = null;

  @SerializedName("user")
  private UserMinalInfoDTO user = null;

  public ServiceLikeDTO id(Integer id) {
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

  public ServiceLikeDTO score(Double score) {
    this.score = score;
    return this;
  }

   /**
   * Get score
   * @return score
  **/
  @ApiModelProperty(value = "")
  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public ServiceLikeDTO dateInsert(DateTime dateInsert) {
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

  public ServiceLikeDTO userId(String userId) {
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(value = "")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ServiceLikeDTO serviceId(Integer serviceId) {
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

  public ServiceLikeDTO rowVersion(String rowVersion) {
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

  public ServiceLikeDTO user(UserMinalInfoDTO user) {
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(value = "")
  public UserMinalInfoDTO getUser() {
    return user;
  }

  public void setUser(UserMinalInfoDTO user) {
    this.user = user;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceLikeDTO serviceLikeDTO = (ServiceLikeDTO) o;
    return Objects.equals(this.id, serviceLikeDTO.id) &&
        Objects.equals(this.score, serviceLikeDTO.score) &&
        Objects.equals(this.dateInsert, serviceLikeDTO.dateInsert) &&
        Objects.equals(this.userId, serviceLikeDTO.userId) &&
        Objects.equals(this.serviceId, serviceLikeDTO.serviceId) &&
        Objects.equals(this.rowVersion, serviceLikeDTO.rowVersion) &&
        Objects.equals(this.user, serviceLikeDTO.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, score, dateInsert, userId, serviceId, rowVersion, user);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceLikeDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
    sb.append("    dateInsert: ").append(toIndentedString(dateInsert)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    rowVersion: ").append(toIndentedString(rowVersion)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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
