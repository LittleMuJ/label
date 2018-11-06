package ${bizBasePackage}.entity;
import java.io.Serializable;

import com.label.common.base.BaseEntity;

/**
* 描述：${table_annotation}模型
* @author ${author}
* @date ${date}
*/

public class ${table_name} extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
    <#if model_column?exists>
        <#list model_column as model>
    /**
    *${model.columnComment!}
    */
    <#if (model.columnType = 'varchar' && model.columnName != 'is_delete' || model.columnType = 'text')>
    private String ${model.changeColumnName?uncap_first};

    </#if>
    <#if (model.columnType = 'int' || model.columnType = 'int4' || model.columnType = 'int8')>
    private Integer ${model.changeColumnName?uncap_first};

    </#if>
    <#if (model.columnType = 'json')>
    private Object ${model.changeColumnName?uncap_first};

    </#if>
    <#if model.columnType = 'timestamp' && model.columnName != 'created_at' && model.columnName != 'updated_at'>
    private Date ${model.changeColumnName?uncap_first};

    </#if>
    <#if model.columnType = 'timestamptz' && model.columnName != 'created_at' && model.columnName != 'updated_at'>
     private Date ${model.changeColumnName?uncap_first};

    </#if>
     <#if model.columnType = 'float8' >
    private Double ${model.changeColumnName?uncap_first};
     </#if>
    <#if model.columnType = 'bool' >
    private boolean ${model.changeColumnName?uncap_first};
    </#if>
        </#list>
    </#if>

<#if model_column?exists>
<#list model_column as model>
<#if (model.columnType = 'varchar' && model.columnName != 'is_delete' || model.columnType = 'text')>
    public String get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(String ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }

</#if>
<#if (model.columnType = 'int4' || model.columnType = 'int' || model.columnType = 'int8')>
    public Integer get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Integer ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }

</#if>
<#if (model.columnType = 'json')>
    public Object get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Object ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }

</#if>
<#if model.columnType = 'timestamp' && model.columnName != 'created_at' && model.columnName != 'updated_at'>
    public Date get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Date ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }

</#if>
<#if model.columnType = 'timestamptz' && model.columnName != 'created_at' && model.columnName != 'updated_at'>
    public Date get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Date ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
</#if>
<#if model.columnType = 'float8' >
    public Double get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Double ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
</#if>
    <#if model.columnType = 'bool' >
    public boolean get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(boolean ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
</#list>
</#if>

}