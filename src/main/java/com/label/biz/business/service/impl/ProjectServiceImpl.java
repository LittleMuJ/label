package com.label.biz.business.service.impl;

import com.label.biz.business.entity.Project;
import com.label.biz.business.service.ProjectService;
import com.label.biz.business.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* 描述：标注平台用户 服务实现层
* @author MuRunSen
* @date 2018-11-06 14:54:01
*/
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;


    @Override
    public Project getById(Integer id) throws Exception {
        Project project = projectDao.getById(id);
        return project;
    }

    @Override
    public void add(Project project) {
       projectDao.add(project);
    }

    @Override
    public void update(Project project){
       projectDao.updateNotNull(project);
    }
    
    @Override
	public void delete(Integer id) {

		projectDao.delete(id);
	}
}