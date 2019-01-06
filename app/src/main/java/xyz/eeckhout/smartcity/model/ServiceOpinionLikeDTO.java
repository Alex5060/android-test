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
 * ServiceOpinionLikeDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-06T14:36:30.645+01:00")
public class ServiceOpinionLikeDTO {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("serviceOpinionId")
  private Integer serviceOpinionId = null;

  @SerializedName("userId")
  private String userId = null;

  @SerializedName("dateLike")
  private DateTime dateLike = null;

  @SerializedName("user")
  private UserMinalInfoDTO user = null;

  public ServiceOpinionLikeDTO id(Integer id) {
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

  public ServiceOpinionLikeDTO serviceOpinionId(Integer serviceOpinionId) {
    this.serviceOpinionId = serviceOpinionId;
    return this;
  }

   /**
   * Get serviceOpinionId
   * @return serviceOpinionId
  **/
  @ApiModelProperty(value = "")
  public Integer getServiceOpinionId() {
    return serviceOpinionId;
  }

  public void setServiceOpinionId(Integer serviceOpinionId) {
    this.serviceOpinionId = serviceOpinionId;
  }

  public ServiceOpinionLikeDTO userId(String userId) {
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

  public ServiceOpinionLikeDTO dateLike(DateTime dateLike) {
    this.dateLike = dateLike;
    return this;
  }

   /**
   * Get dateLike
   * @return dateLike
  **/
  @ApiModelProperty(value = "")
  public DateTime getDateLike() {
    return dateLike;
  }

  public void setDateLike(DateTime dateLike) {
    this.dateLike = dateLike;
  }

  public ServiceOpinionLikeDTO user(UserMinalInfoDTO user) {
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
    ServiceOpinionLikeDTO serviceOpinionLikeDTO = (ServiceOpinionLikeDTO) o;
    return Objects.equals(this.id, serviceOpinionLikeDTO.id) &&
        Objects.equals(this.serviceOpinionId, serviceOpinionLikeDTO.serviceOpinionId) &&
        Objects.equals(this.userId, serviceOpinionLikeDTO.userId) &&
        Objects.equals(this.dateLike, serviceOpinionLikeDTO.dateLike) &&
        Objects.equals(this.user, serviceOpinionLikeDTO.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, serviceOpinionId, userId, dateLike, user);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceOpinionLikeDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    serviceOpinionId: ").append(toIndentedString(serviceOpinionId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    dateLike: ").append(toIndentedString(dateLike)).append("\n");
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

