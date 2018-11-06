package com.label.web.business.controller;
import com.label.biz.business.entity.Project;
import com.label.biz.business.service.ProjectService;
import com.label.common.base.BaseController;
import com.label.common.util.Code;
import com.label.common.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

/**
* 描述：标注平台用户controller
* @author MuRunSen
* @date 2018-11-06 14:54:01
*/
@Controller
@RequestMapping("/api/project")
public class ProjectController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    /**
    * 描述：根据Id 查询
    * @param id  标注平台用户id
    */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object findById(@PathVariable("id") Integer id)throws Exception {
        Project project = projectService.getById(id);
        
        return actionResult(Code.OK,project);
    }

    /**
    * 描述:创建标注平台用户
    * 保存
    * @param project  标注平台用户
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object create(@RequestBody Project project) throws Exception {
    try {
        // 修改
		if(ValidateUtil.isNotEmpty(project.getId())){
			
			projectService.update(project);
		}
		// 新增
			else{
				projectService.add(project);
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}
        return actionResult(Code.OK);
    }

    /**
    * 描述：删除标注平台用户
    * @param id 标注平台用户id
    */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object deleteById(@PathVariable("id") Integer id) throws Exception {
    try {
        	projectService.delete(id);
            return actionResult(Code.OK);
    	} catch (Exception e) {
		
		logger.error(e.getMessage(), e);
		return actionResult(Code.INTERNAL_SERVER_ERROR);
	}
  }
}