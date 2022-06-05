package com.mike.controller;

import com.mike.utils.foperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: 23236
 * @date: 2021/3/28 21:59
 * @description:
 */
@Controller
public class FileController {

    @ResponseBody
    @RequestMapping(value = "/img/{imageName}")
    public void showImg(HttpServletResponse response,@PathVariable(value = "imageName")String imageName){
        String path= foperation.IMG_PATH+imageName;
        File file=new File(path);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            //注意, jdk8中还没有这个方法,使用jdk8的小伙伴要自己想办法复制流
            response.setContentType("image/*");
            inputStream.transferTo(outputStream);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/book/{bookName}")
    public void downLoadBook(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "bookName")String bookName, Model model){
//        User user = (User) request.getSession().getAttribute("user");
        System.out.println("bookName="+bookName);
        response=foperation.downloadBook(response,bookName);
    }
}
