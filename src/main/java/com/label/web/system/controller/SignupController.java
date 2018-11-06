package com.label.web.system.controller;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.label.biz.system.entity.Admin;
import com.label.biz.system.service.AdminService;
import com.label.common.base.BaseController;
import com.label.common.interceptor.JwtManager;
import com.label.common.sms.SmsContentUtil;
import com.label.common.sms.SmsSingleSender;
import com.label.common.util.Code;
import com.label.common.util.MD5Util;
import com.label.common.util.RandomUtil;
import com.label.common.util.SysMessage;
import com.label.redis.service.RedisInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: MuRunSen
 * @Date: 2018/11/2 16:05
 */
@Controller
@RequestMapping("/api/signup")
public class SignupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisInfoService redisService;

    @RequestMapping(value="/")
    public String init(HttpServletRequest request){

        logger.info("跳转到注册页面");
        return "index";
    }

    /**
     * 1.1标注数据管理后台的管理员用户注册
     * @param admin
     * @return
     */
    @RequestMapping(value = "/signUpAdmin",method = RequestMethod.POST)
    @ResponseBody
    public Object signUpAdmin(HttpServletResponse response,@Valid Admin admin, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return actionResult(Code.BAD_REQUEST,"请完成表单填写");
        }
        //查询redis验证码是否正确
        if(!admin.getvCode().equals(redisService.getString(admin.getPhone()).toString())) {
            return actionResult(Code.BAD_REQUEST,SysMessage.VERIFICATION_CODE_ERROR);
        }
        //注册前检查用户名和电话号码是否被注册是否重复
        Admin param = new Admin();
        param = adminService.getByParam(admin);
        if(param!=null) {
            if(param.getPhone().equals(admin.getPhone())){
                return actionResult(Code.BAD_REQUEST,SysMessage.SIGNUP_PHONE_EXIST);
            }
            if(param.getUsername().equals(admin.getUsername())){
                return actionResult(Code.BAD_REQUEST,SysMessage.SIGNUP_USER_EXIST);
            }
        }
        //获取十位随机串
        String randStr = RandomUtil.createRandomChar(10);
        //对用户表单输入密码进行首次md5加密
        String md5Password = MD5Util.MD5(admin.getPassword());
        //将加密过的password进行与随机数randStr进行一次md5加密
        String passWordandSalt = MD5Util.MD5(md5Password+randStr);
        //将随机生成的字符串截取前五位与后五位
        String before5 = randStr.substring(0, 5);
        String after5 = randStr.substring(5);
        //最终用户存进数据库的密码
        String user_password = before5+passWordandSalt+after5;
        admin.setPassword(user_password);

        try {
            adminService.insertAndGetId(admin);
            int userId = admin.getId();
            String token = JwtManager.createToken(admin.getUsername(),userId,SysMessage.ADMIN);
            response.addHeader("Set-Cookie", "Token="+token+"; Path=/; HttpOnly");
            return actionResult(Code.OK,"注册成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return actionResult(Code.INTERNAL_SERVER_ERROR,"注册失败");
        }
    }

    /**
     * 1.2.短信验证码接口
     */
    @RequestMapping(value={"/obtainSms"},method=RequestMethod.POST )
    @ResponseBody
    public Object obtainSms(String number){
        SmsSingleSender ssender = new SmsSingleSender(SmsContentUtil.APPID, SmsContentUtil.APPKEY);

        try {

            SmsSingleSenderResult result = ssender.sendWithParam(SmsContentUtil.nationCode,number,SmsContentUtil.TTEMPLATEID,SmsContentUtil.list,SmsContentUtil.SMSSIGN,"","");
            if(result.errMsg.equals("OK")) {
                logger.info("电话"+number+",验证码："+SmsContentUtil.list.get(0));
                //如果发送成功存入redis,设置过期时间为300秒
                redisService.set(number, SmsContentUtil.list.get(0),300,TimeUnit.SECONDS);
                //redisService.setString(number, SmsContentUtil.list.get(0));
                return actionResult(Code.OK,result.errMsg);
            }else {
                return actionResult(Code.BAD_REQUEST,result.errMsg);
            }
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
