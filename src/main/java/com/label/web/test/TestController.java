package com.label.web.test;

import com.label.common.base.BaseController;
import com.label.common.interceptor.JwtManager;
import com.label.common.util.Code;
import com.label.common.util.SysMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MuRunSen
 * @Date: 2018/10/29 15:40
 */
@Controller
public class TestController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/")
    public Object Test(){
        return "index";
    }

    @RequestMapping(value = "/login",method =RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, String userName, String password){
        logger.info("userName:"+userName);
        logger.info("password:"+password);

        return actionResult(Code.OK,"登录成功",JwtManager.createToken(userName,12,SysMessage.ADMIN));
    }


    @RequestMapping(value = "/findData",method = RequestMethod.POST)
    @ResponseBody
    public Object findData(Integer userId){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        return actionResult(Code.OK,"获取成功",map);
    }
}
