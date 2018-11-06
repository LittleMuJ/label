package ${bizBasePackage}.dao;
import org.apache.ibatis.annotations.Mapper;

import com.label.common.base.BaseDao;
import ${bizBasePackage}.entity.${table_name};

/**
* 描述：${table_annotation}DTO
* @author ${author}
* @date ${date}
*/
@Mapper
public interface ${table_name}Dao extends BaseDao<${table_name}, Integer>{

}