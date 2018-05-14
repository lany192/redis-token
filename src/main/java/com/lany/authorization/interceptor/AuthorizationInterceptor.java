package com.lany.authorization.interceptor;

import com.lany.authorization.annotation.Authorization;
import com.lany.authorization.manager.TokenManager;
import com.lany.authorization.model.TokenModel;
import com.lany.config.Constants;
import com.lany.config.ResultStatus;
import com.lany.model.ResponseResult;
import com.lany.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 *
 * @see Authorization
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(Authorization.class) != null) {
            //从header中得到token
            String token = request.getParameter("token");
            log.info("接收到的token:" + token);
            if (!StringUtils.isEmpty(token)) {
                //验证token
                TokenModel model = manager.getTokenInfoByToken(token);
                if (model != null) {
                    log.info("token验证通过");
                    //如果token验证成功，将token对应的用户id存在request中，便于之后注入
                    request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
                    return true;
                }
            }
            log.info("token验证失败");
            response.setContentType("application/json;charset=utf-8");
            ResponseResult result = new ResponseResult(ResultStatus.SC_UNAUTHORIZED, "token过期，请重新登录！");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(result.toString());
            printWriter.flush();
            return false;
        }
        return true;
    }
}
