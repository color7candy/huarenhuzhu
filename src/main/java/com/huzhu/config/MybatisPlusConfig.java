package com.huzhu.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.huzhu.common.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mybatisplus的配置类
 *
 * @author lp
 */
@Configuration
public class MybatisPlusConfig implements WebMvcConfigurer {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        interceptor.addInnerInterceptor(paginationInterceptor);
        return interceptor;
    }
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/huzhu/**")
                .excludePathPatterns("/", "/**/login","/login/**", "/**/register", "/**/forgot", "/**/forgotpwd", "/resources/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
