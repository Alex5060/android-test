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

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * ServiceOpinionMinDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-06T14:36:30.645+01:00")
public class ServiceOpinionMinDTO {
  @SerializedName("comment")
  private String comment = null;

  @SerializedName("answerId")
  private Integer answerId = null;

  public ServiceOpinionMinDTO comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @ApiModelProperty(required = true, value = "")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public ServiceOpinionMinDTO answerId(Integer answerId) {
    this.answerId = answerId;
    return this;
  }

   /**
   * Get answerId
   * @return answerId
  **/
  @ApiModelProperty(value = "")
  public Integer getAnswerId() {
    return answerId;
  }

  public void setAnswerId(Integer answerId) {
    this.answerId = answerId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceOpinionMinDTO serviceOpinionMinDTO = (ServiceOpinionMinDTO) o;
    return Objects.equals(this.comment, serviceOpinionMinDTO.comment) &&
        Objects.equals(this.answerId, serviceOpinionMinDTO.answerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, answerId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceOpinionMinDTO {\n");
    
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    answerId: ").append(toIndentedString(answerId)).append("\n");
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

