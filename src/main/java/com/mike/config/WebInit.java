package com.mike.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @Author: 23236
 * @createTime: 2022/9/28   20:42
 * @description: 配置类
 * 用来代替web.xml类
 *
 *
 **/

//@Configuration
//@ComponentScan(basePackages = {""})
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
//        获取根配置类，指定spring的配置类
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
//        配置springmvc的配置类
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
//        获取dispatcherservlet的映射路径，url指定映射规则
        return new String[0];
    }

    @Override
    protected Filter[] getServletFilters() {
//        注册过滤器
        return super.getServletFilters();
    }
}
