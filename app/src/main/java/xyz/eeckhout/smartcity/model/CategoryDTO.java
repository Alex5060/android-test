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
 * CategoryDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-06T14:36:30.645+01:00")
public class CategoryDTO {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("rowVersion")
  private byte[] rowVersion = null;

  @SerializedName("name")
  private String name = null;

  public CategoryDTO id(Integer id) {
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

  public CategoryDTO rowVersion(byte[] rowVersion) {
    this.rowVersion = rowVersion;
    return this;
  }

   /**
   * Get rowVersion
   * @return rowVersion
  **/
  @ApiModelProperty(value = "")
  public byte[] getRowVersion() {
    return rowVersion;
  }

  public void setRowVersion(byte[] rowVersion) {
    this.rowVersion = rowVersion;
  }

  public CategoryDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
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
    CategoryDTO categoryDTO = (CategoryDTO) o;
    return Objects.equals(this.id, categoryDTO.id) &&
        Objects.equals(this.rowVersion, categoryDTO.rowVersion) &&
        Objects.equals(this.name, categoryDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, rowVersion, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategoryDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

