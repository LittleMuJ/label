package com.label.web.system.controller;

import com.label.biz.system.entity.Admin;
import com.label.biz.system.service.AdminService;
import com.label.common.base.BaseController;
import com.label.common.interceptor.JwtManager;
import com.label.common.util.Code;
import com.label.common.util.MD5Util;
import com.label.common.util.SysMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: MuRunSen
 * @Date: 2018/11/5 10:06
 */
@RestController
@RequestMapping("/api/login")
public class LoginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminService adminService;


    /**
     * 2.1管理员登录接口
     * @param request
     * @param response
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/checkAdminLogin",method = RequestMethod.POST)
    public Object checkAdminLogin(HttpServletRequest request, HttpServletResponse response, String username, String password){
        try {
            Admin admin = new Admin();
            admin.setUsername(username);
            admin= adminService.getByParam(admin);
            if(admin==null){
                return actionResult(Code.BAD_REQUEST,SysMessage.LOGIN_USER_NOT_EXIST);
            }
            String	userPassword = admin.getPassword();
            Integer userId = admin.getId();
            //首先對用戶表單中的password進行一次MD5加密
            String MD5password = MD5Util.MD5(password);
            //获取前五位
            String before5 = userPassword.substring(0, 5);
            //获取后五位
            String after5 = userPassword.substring(userPassword.length()-5);
            //获取剔除盐之后的password
            String pwd =  userPassword.substring(5, userPassword.length()-5);
            //生成注册時隨機生成的盐
            String salt = before5+after5;
            String passWordandSalt = MD5Util.MD5(MD5password+salt);
            //验证密码
            if(!pwd.equals(passWordandSalt)) {
                logger.info(username + SysMessage.LOGIN_USER_INFO_ERROR);
                return actionResult(Code.BAD_REQUEST,SysMessage.LOGIN_USER_INFO_ERROR);
            }
            logger.info(username + SysMessage.LOGIN_SUCCESS);

            String token =JwtManager.createToken(username,userId,SysMessage.ADMIN);

            response.addHeader("Set-Cookie", "Token="+token+"; Path=/; HttpOnly");

            return actionResult(Code.OK,SysMessage.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return actionResult(Code.INTERNAL_SERVER_ERROR,SysMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
