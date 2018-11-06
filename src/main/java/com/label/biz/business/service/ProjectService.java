package com.label.biz.business.service;

import com.label.biz.business.entity.Project;

/**
* 描述：标注平台用户 服务实现层接口
* @author MuRunSen
* @date 2018-11-06 14:54:01
*/
public interface ProjectService {

    /**
    * 描述：根据Id获取DTO
    * @param id
    */
    Project getById(Integer id)throws Exception;

    //ProjectDTO createProject(ProjectDTO projectDTO) throws Exception;
    void add(Project project);

    void delete(Integer id) throws Exception;

    //ProjectDTO updateProject(ProjectDTO projectDTO) throws Exception;
    void update(Project project);

}