package ${basePackageEntity};

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
* @Description:${entityName}
* @Param:
* @return:
* @Author: ${author}
* @Date: ${date}
*/
@Entity
@Table(name = "${tableName}", schema = "${schemaName}", catalog = "")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class ${entityName} implements Serializable{
    private static final long serialVersionUID = 1L;

<#list columnList as column>
    <#--如果想在循环中得到索引，使用循环变量+_index就可以得到。-->
    <#if column.PK == true>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "${column.COLUMN_NAME}")
    <#else>
    @Basic
    @Column(name = "${column.COLUMN_NAME}")
    </#if>
    <#if column.IS_NULLABLE == "NO">
    @NotNull
    </#if>
    @ApiModelProperty(value = "${column.REMARKS}"<#if column.DATA_TYPE == "Integer">, example = "0"</#if>)
    private ${column.DATA_TYPE} ${column.JAVA_BEAN}; //${column.REMARKS}

</#list>
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
<#--如果想要getset则放开以下注释代码-->
<#--<#list columnList as column>-->
    <#--public ${column.DATA_TYPE} ${column.GET_BEAN}() {-->
      <#--return ${column.JAVA_BEAN};-->
    <#--}-->

    <#--public void ${column.SET_BEAN}(${column.DATA_TYPE} ${column.JAVA_BEAN}) {-->
      <#--this.${column.JAVA_BEAN} = ${column.JAVA_BEAN};-->
    <#--}-->

<#--</#list>-->
}