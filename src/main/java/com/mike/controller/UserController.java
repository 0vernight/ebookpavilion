package com.mike.controller;

import com.mike.DTO.BaseResponse;
import com.mike.bean.User;
import com.mike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/15 18:12
 * @description:
 */


    @RestController
public class UserController {
    @Autowired
    User user;

    @Autowired
    UserService userService;


    @RequestMapping("/userall")
    public BaseResponse<List> selectAll(@RequestParam(value = "name",required = false)String name){
        return userService.selectAll();
    }


    @RequestMapping("/login")
    public BaseResponse<User> login(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
//        前端用Ajax请求判断用户名和密码对了才跳转到首页，否则让用户重新输入“不跳转”
        BaseResponse<User> baseResponse = userService.loginByUsernamePass(user);
        if (baseResponse.getCode()==200){
//            session中保存整个user
            request.getSession().setAttribute("user",baseResponse.getData());
//            System.out.println("login sessionId="+request.getSession().getId());
//            System.out.println("sessionGetAttribute="+request.getSession().getAttribute("user"));
//            System.out.println(baseResponse.getData());
        }
        return baseResponse;
    }


    @RequestMapping("/register")
    public BaseResponse<User> register(User user,String repassword, HttpServletRequest request,Model model) {
//        System.out.println(user+"repassword"+repassword);
        BaseResponse<User> response=userService.register(user);
        return response;
    }
//    直接跳转的时候完成了webcontroller，所以这个控制器没用
    @RequestMapping("/showProfile")
    public BaseResponse<User> showProfile(User user, HttpServletRequest request,Model model) {
        BaseResponse<User> response=userService.getUserInfoByID(user);
//        model里面放入baserespose能在前端去得到的吗？
        model.addAttribute("user",response.getData());
        return response;
    }
    @RequestMapping("/alterProfile")
    public BaseResponse<User> alterProfile(User user,HttpServletRequest request,Model model) {
        System.out.println("alterpfofile="+user);
        BaseResponse<User> response=userService.alterProfile(user);
//        model里面放入baserespose能在前端去得到的吗？
        model.addAttribute("user",response.getData());
        return response;
    }
    @RequestMapping("/updateavatar")
    public BaseResponse<User> updateAvatar(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, Model model) throws IOException {
        BaseResponse<User> response=new BaseResponse<>();
        User user = (User) request.getSession().getAttribute("user");
        if (multipartFile.isEmpty()){
            System.out.println("文件是空的");
            response.setError("上传的文件为空",user);
        }else if (user==null){
            System.out.println("用户头像不能为空");
        }else {
            response=userService.updateAvatar(user,multipartFile);
        }
        System.out.println("update avatar="+user);
        model.addAttribute("user",user);
        return response;
    }
    @RequestMapping("/refactorPassword")
    public BaseResponse<User> refactorPassword(User user,String newPass,HttpServletRequest request,Model model) {
//        User user = (User) request.getSession().getAttribute("user");
        ServletContext context = request.getServletContext();
        final ServletContext context1 = context.getContext("/alterProfile");
        final Object user1 = context.getAttribute("user");
        String path = context.getRealPath("");
        System.out.println("real path="+path+"/n"+user1+"/n"+context1);

        BaseResponse<User> response=userService.refactorPassword(user,newPass);
//        model里面放入baserespose能在前端去得到的吗？
        model.addAttribute("user",response.getData());
        return response;

    }




}