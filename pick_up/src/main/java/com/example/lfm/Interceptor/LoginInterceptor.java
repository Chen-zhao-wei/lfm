package com.example.lfm.Interceptor;

import com.example.lfm.utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.example.lfm.utils.JwtTokenUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    /**
     * 进入controller前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("############进入用户token验证拦截器");
        String uri = request.getRequestURI().toString();
        log.info(uri);
        String token = request.getHeader("x-auth-token");
        log.info(token);
        if(token==null){
            return false;
        }
        String studentName= JwtTokenUtils.getStudentName(token);
        if (null == token){
            log.error("##########当前用户未登录");
            return false;
        }
        if( StringUtils.isEmpty(redisCache.getCacheObject(studentName))){
            log.error("##########当前用户token在redis中不存在,studentName:{}",studentName);
            return false;
        }

        Long expire = stringRedisTemplate.getExpire(studentName);
        log.info(studentName+"=======");
        log.info(expire+"++++++++++=======");
        if(expire <= 0){
            log.error("##########当前用户token已失效,studentName:{}",studentName);
            return false;
        }

        //为当前登录用户重置登录活性
        redisCache.setCacheObject(studentName,token,30,TimeUnit.MINUTES);

        return true;
    }
}