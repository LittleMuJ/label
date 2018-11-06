package com.label.web.system.controller;
import com.label.biz.business.entity.Classify;
import com.label.biz.business.service.ClassifyService;
import com.label.biz.system.service.AdminService;
import com.label.biz.system.entity.Admin;
import com.label.common.base.BaseController;

import com.label.common.model.PageModel;
import com.label.common.util.Code;
import com.label.common.util.SysMessage;
import com.label.common.util.ValidateUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
* @date 2018-11-02 11:07:14
*/
@Controller
@RequestMapping("/api/admin")
public class AdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ClassifyService classifyService;

    /**
    * 描述：根据Id 查询
    * @param id  标注平台用户id
    */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object findById(@PathVariable("id") Integer id)throws Exception {
        Admin admin = adminService.getById(id);
        
        return actionResult(Code.OK,admin);
    }

    /**
    * 描述:创建标注平台用户
    * 保存
    * @param admin  标注平台用户
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object create(@RequestBody Admin admin) throws Exception {
    try {
        // 修改
		if(ValidateUtil.isNotEmpty(admin.getId())){
			
			adminService.update(admin);
		}
		// 新增
			else{
				adminService.add(admin);
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
        	adminService.delete(id);
            return actionResult(Code.OK);
    	} catch (Exception e) {
		
		logger.error(e.getMessage(), e);
		return actionResult(Code.INTERNAL_SERVER_ERROR);
	}
  }

    /**
     * 3.1 管理员获取标注类型接口
     * @param classify
     * @param page
     * @return
     */
    @RequestMapping(value = "/classifyList",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(SysMessage.ADMIN)
    @RequiresAuthentication
    public Object classifyList(Classify classify, PageModel<Classify> page){

        try {
            PageModel<Classify> pages = classifyService.findByPage(classify,page);
            return actionResult(Code.OK,"获取成功",pages);
        } catch (Exception e) {
            e.printStackTrace();
            return actionResult(Code.INTERNAL_SERVER_ERROR,"获取失败");
        }
    }
}