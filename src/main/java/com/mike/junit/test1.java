package com.mike.junit;

import com.mike.bean.Page;
import com.mike.mapper.BookMapper;
import com.mike.service.impl.BookServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 23236
 * @createTime: 2022/6/21   22:39
 * @description: 测试类
 **/
@Service
public class test1 {
    @Autowired
    BookMapper bookMapper;
    BookServiceImpl bookService=new BookServiceImpl();
    @Autowired
    Page pp ;
    @Test
    public void pageItems(){
        System.out.println(pp);
        System.out.println("log for="+pp.getMeta());
    }



}
