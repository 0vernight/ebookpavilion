package com.mike.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/27 9:10
 * @description:
 */


public class foperation {
    public static  final String BOOK_PATH="F:\\upload\\book\\";
    public static  final String IMG_PATH="F:\\upload\\img\\";

    public static String uploadImg(MultipartFile multipartFile){
        List<String> imageType = new ArrayList<String>(Arrays.asList(".jpg",".jpeg", ".png", ".bmp", ".gif"));
        // 获取文件名，带后缀Lists.newArrayList("jpg","jpeg", "png", "bmp", "gif");
        String originalFilename = multipartFile.getOriginalFilename();
        // 获取文件的后缀格式
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (imageType.contains(fileSuffix)) {
            // 只有当满足图片格式时才进来，重新赋图片名，防止出现名称重复的情况
            String newFileName = UUIDUtils.getUUID32() + fileSuffix;

            File destFile = new File(IMG_PATH + newFileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                multipartFile.transferTo(destFile);
                // 将相对路径返回给service
                return "\\img\\"+ newFileName;
            } catch (IOException e) {
                return null;
            }
        } else {
            // 非法文件throw new NotifyException(ExceptionConstants.FILE_UPLOAD_ERROR);
            System.out.println("非法文件");
            return null;
        }
    }

    public static String uplodBook(MultipartFile multipartFile){
        List<String> fileTypes = new ArrayList<String>(Arrays.asList("txt","pdf","doc","docx"));
        // 获取文件名，带后缀Lists.newArrayList("txt");
        String originalFilename = multipartFile.getOriginalFilename();
        // 获取文件的后缀格式
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (fileTypes.contains(fileSuffix)) {
            // 只有当满足图片格式时才进来，重新赋图片名，防止出现名称重复的情况
            String newFileName = UUIDUtils.getUUID32() + originalFilename;
            File destFile = new File(BOOK_PATH + newFileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                multipartFile.transferTo(destFile);
                // 将相对路径返回给service
                return "\\book\\"+ newFileName;
            } catch (IOException e) {
                return null;
            }
        } else {
            // 非法文件throw new NotifyException(ExceptionConstants.FILE_UPLOAD_ERROR);
            System.out.println("非法文件");
            return null;
        }
    }
    public static HttpServletResponse downloadBook(HttpServletResponse response, String bookName){
        String path= BOOK_PATH+bookName;
        File file=new File(path);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(bookName, "UTF-8"));
            //注意, jdk8中还没有这个方法,使用jdk8的小伙伴要自己想办法复制流
            inputStream.transferTo(outputStream);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
