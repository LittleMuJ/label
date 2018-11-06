package ${bizBasePackage}.service;

import ${bizBasePackage}.entity.${table_name};
import com.label.common.model.PageModel;
/**
* 描述：${table_annotation} 服务实现层接口
* @author ${author}
* @date ${date}
*/
public interface ${table_name}Service {

    /**
    * 描述：根据Id获取DTO
    * @param id
    */
    ${table_name} getById(Integer id)throws Exception;

    //${table_name}DTO create${table_name}(${table_name}DTO ${table_name?uncap_first}DTO) throws Exception;
    void add(${table_name} ${table_name?uncap_first});

    void delete(Integer id) throws Exception;

    //${table_name}DTO update${table_name}(${table_name}DTO ${table_name?uncap_first}DTO) throws Exception;
    void update(${table_name} ${table_name?uncap_first});

    PageModel<${table_name}> findByPage(${table_name} ${table_name?uncap_first}, PageModel<${table_name}> page);

}