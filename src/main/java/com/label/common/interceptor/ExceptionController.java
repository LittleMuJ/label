package com.label.common.interceptor;

import com.label.common.base.BaseController;
import com.label.common.util.Code;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常拦截统一处理Controller
 */
@RestControllerAdvice
public class ExceptionController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    // 捕捉 CustomRealm 抛出的异常
    @ExceptionHandler(AccountException.class)
    public Object handleShiroException(Exception ex) {
        return actionResult(Code.UNAUTHORIZED,ex.getMessage());
    }

   /* @ExceptionHandler(ShiroException.class)
    public Object handle401() {
        return actionResult(Code.BAD_REQUEST,"您没有权限访问！");
    }*/

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Object handle401(ShiroException e, ServletRequest request, ServletResponse response) {
        logger.info("捕捉shiro的异常，Token过期验证失败，跳转登录页");
        /*responselogin(request,response);*/
        return  actionResult(Code.UNAUTHORIZED, "Token验证失败，请重新登录", e.getMessage());
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Object handle401(UnauthorizedException u,ServletRequest request, ServletResponse response) {
        logger.info("捕捉UnauthorizedException,Token过期验证失败,跳转登录页");
        //responselogin(request,response);
        return actionResult(Code.UNAUTHORIZED, "Unauthorized", u.getMessage());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object globalException(HttpServletRequest request, Throwable ex) {
        return actionResult(Code.BAD_REQUEST, getStatus(request), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 将非法请求跳转到登录页
     */
    private void responselogin(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;

            Cookie newCookie=new Cookie("Token",null); //假如要删除名称为username的Cookie

            newCookie.setMaxAge(0); //立即删除型

            newCookie.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除

            //httpServletResponse.addHeader("Set-Cookie", "Token=; Path=/; HttpOnly");
            httpServletResponse.addCookie(newCookie); //重新写入，将覆盖之前的
            httpServletResponse.sendRedirect("/");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
