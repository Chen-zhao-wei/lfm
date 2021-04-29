package com.example.lfm.config;

import com.example.lfm.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {

    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * 解决跨域请求问题
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    /**
     * 添加token拦截器
     *
     * @param registry
     */
    /**
     * 配置静态资源
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        /*放行swagger*/
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                /*放行swagger*/
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .excludePathPatterns("/app/register1") //用户注册
                .excludePathPatterns("/app/register")
                .excludePathPatterns("/app/getAuthCode")
                .excludePathPatterns("/app/getSchool")
                .excludePathPatterns("/print/upload")
                .excludePathPatterns("/print/notify_url")
                .excludePathPatterns("/app/uploadAvatar")
                .excludePathPatterns("/app/login"); //登录页

              /*   .excludePathPatterns("/hlladmin/user/sendEmail") //发送邮箱
                .excludePathPatterns("/hlladmin/user/register")
                .excludePathPatterns("/hlladmin/user/login"); //用户登录*/
        super.addInterceptors(registry);
    }

}
