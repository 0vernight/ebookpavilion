package com.mike.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author: 23236
 * @createTime: 2022/7/6   21:50
 * @description: 从前台传过来的值注入到bean对象当中
 **/
public class WebBeanUtils {
//    public  static void utilsPopulate(HttpServletRequest request,Object bean){
    //优化后，解耦行更好
    public  static <T> T utilsPopulate(Map map, T bean){
        try {
            BeanUtils.populate(bean,map);
            return bean;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
