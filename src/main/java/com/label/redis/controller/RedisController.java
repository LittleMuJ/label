package com.label.redis.controller;

import com.label.biz.system.entity.Admin;
import com.label.biz.system.service.AdminService;
import com.label.common.base.BaseController;
import com.label.common.util.Code;
import com.label.common.util.SysMessage;
import com.label.redis.service.RedisInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: MuRunSen
 * @Date: 2018/11/2 15:52
 */
@RestController
public class RedisController extends BaseController {
    @Autowired
    private RedisInfoService redisService;

    @Autowired
    private AdminService adminService;

    @RequestMapping("/setString")
    public String setString(String key,String value){
        try {
            redisService.setString(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis存储值异常");
        }
        return "SUCCESS";
    }

    @RequestMapping("/findByParam")
    public Object findByParam(String username,String phone){
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPhone(phone);
        //注册前检查用户名是否重复
        if(adminService.getByParam(admin)!=null) {
            return actionResult(Code.BAD_REQUEST,SysMessage.SIGNUP_USER_EXIST);
        }
        //注册前检查电话号码是否已被使用
        if(adminService.getByParam(admin)!=null) {
            return actionResult(Code.BAD_REQUEST,SysMessage.SIGNUP_PHONE_EXIST);
        }
        return "可以注册";
    }
}
