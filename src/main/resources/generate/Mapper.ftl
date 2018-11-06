<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${bizBasePackage}.dao.${table_name}Dao">
    <parameterMap id="parameterMap" type="${bizBasePackage}.entity.${table_name}"></parameterMap>
    <resultMap id="${table_name}ResultMap" type="${bizBasePackage}.entity.${table_name}"></resultMap>


    <sql id="columnSql">
		<trim suffixOverrides=",">
		<#list columns as po>
			t."${po.columnName}",
		</#list>
	 	</trim>
	</sql>
	
    <select id="getById" parameterType="Integer" resultMap="${table_name}ResultMap">
        select
			<include refid="columnSql"></include>
		from ${table_name_small} t
		where t.${pkField} = ${'#'}{value}
    </select>



    <insert id="add" parameterMap="parameterMap">
		insert into ${table_name_small}
		<trim prefix="(" suffix=")" suffixOverrides=",">
			<#list columns as po>
			${po.columnName},
			</#list>
		</trim>
		<trim prefix="values (" suffix=")" suffixOverrides=",">
			<#list model_column as po>
			${'#'}{${po.changeColumnName?uncap_first}},
			</#list>
		</trim>
	</insert>
	
	
	<update id="updateNotNull" parameterMap="parameterMap">
		update ${table_name_small}
		<set>
			<#list columns as po>
			<if test="${po.changeColumnName?uncap_first} != null">
			${po.columnName} = ${'#'}{${po.changeColumnName?uncap_first}},
			</if>
			</#list>
		</set>
		where ${pkField} = ${'#'}{${pkField}}
	</update>

	
	<delete id="delete" parameterType="java.lang.Integer">
		delete from ${table_name_small} where ${pkField} = ${'#'}{value}
	</delete>

    <select id="findByPage" parameterMap="parameterMap" resultMap="${table_name}ResultMap">
        select
        <include refid="columnSql"></include>
        from ${table_name_small} t
        <where>
            1=1
            <#list columns as po>
			<if test="${po.changeColumnName?uncap_first} != null">
             and  ${po.columnName} = ${'#'}{${po.changeColumnName?uncap_first}},
            </if>
            </#list>
        </where>
    </select>
</mapper>