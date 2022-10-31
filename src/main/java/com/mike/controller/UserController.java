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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

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
        System.out.println("---------------------------");
        System.out.println(user);
        System.out.println("---------------------------");
        return userService.selectAll();
    }


    @RequestMapping("/login")
    public BaseResponse<User> login(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
//        前端用Ajax请求判断用户名和密码对了才跳转到首页，否则让用户重新输入“不跳转”
        System.out.println("rememberme="+request.getParameter("rememberme"));
        String password = user.getPassword();
        BaseResponse<User> baseResponse = userService.loginByEmailPass(user);
        System.out.println("password down="+ password);
        if (baseResponse.getCode()==200){
            //创建cookie保存用户名和密码
            Cookie cookieUsername = new Cookie("username", baseResponse.getData().getUsername());
            Cookie cookieEmail = new Cookie("email", baseResponse.getData().getEmail());
            Cookie rememberme = new Cookie("rememberme", request.getParameter("rememberme")==null?"false":"true");
            if (request.getParameter("rememberme")!=null) {
                Cookie cookiePassword = new Cookie("password", password);
                cookiePassword.setMaxAge(60*60*24*7);
                response.addCookie(cookiePassword);
            }else {
                Cookie cookiePassword = new Cookie("password", null);
                cookiePassword.setMaxAge(60*60*24*7);
                response.addCookie(cookiePassword);
            }
            cookieUsername.setMaxAge(60*60*24*7);
            cookieEmail.setMaxAge(60*60*24*7);
            rememberme.setMaxAge(60*60*24*7);
            response.addCookie(cookieUsername);
            response.addCookie(cookieEmail);
            response.addCookie(rememberme);
//            session中保存整个user
            request.getSession().setAttribute("user",baseResponse.getData());
//            System.out.println("login sessionId="+request.getSession().getId());
//            System.out.println("sessionGetAttribute="+request.getSession().getAttribute("user"));
//            System.out.println(baseResponse.getData());
        }
        return baseResponse;
    }


    @RequestMapping("/register")
    public BaseResponse<User> register(User user,String repassword, HttpServletRequest request,HttpServletResponse response,Model model) {
//        System.out.println(user+"repassword"+repassword);
//        遍历：keySet() / values() / entrySet()
//        Map<String, String[]> parameterMap = request.getParameterMap();
////        System.out.println(parameterMap);
//        System.out.println("**********************");

//        Set setEntry=parameterMap.entrySet();
//        Iterator iterator = setEntry.iterator();
//        while (iterator.hasNext()){
//            Map.Entry me=(Map.Entry)iterator.next();
//            System.out.println("in the it="+me.getKey() + me.getValue());
//        }

        String auth = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        String auth1 = request.getParameter("auth");

        System.out.println("session="+auth);
        System.out.println("input="+request.getParameter("auth"));
        BaseResponse<User> res=new BaseResponse<>();
        if (request.getParameter("auth").equalsIgnoreCase(auth)) {
            //用完了即删除
            request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);      //删除验证码
            res=userService.register(user);
        }else if(auth!=null&&auth !=request.getParameter("auth")){
            res.setError("验证码输入错误！");
        }else {
            res.setError("不能重复提交表单！");
        }
        return res;
    }
    @RequestMapping("/serchByName")
    public BaseResponse<User> searchName(User user,@RequestParam(value = "username",required = true)String username, HttpServletRequest request,Model model) {
        System.out.println(request.getSession().getAttributeNames());
//        System.out.println(model.getAttribute("userName"));
//        System.out.println("username="+username);
//        System.out.println(user);
        user.setUsername(username);
        BaseResponse<User> response=userService.searchByName(user);


        System.out.println(model);
        Map<String, String[]> parameterMap = request.getParameterMap();
//        System.out.println(parameterMap);

        Set setEntry=parameterMap.entrySet();
        Iterator iterator = setEntry.iterator();
        while (iterator.hasNext()){
            Map.Entry me=(Map.Entry)iterator.next();
            System.out.println("in the it="+me.getKey() +"--->"+me.getValue());
        }
        Map<String, Object> mapModel = model.asMap();
        for (Map.Entry<String, Object> entry : mapModel.entrySet()) {
            System.out.println("model="+entry.getKey()+" value="+ entry.getValue());

        }


        return response;
    }
    @RequestMapping("/searchByEmail")
    public BaseResponse<User> searchByEmail(User user,@RequestParam(value = "email",required = true)String email, HttpServletRequest request,Model model) {
        System.out.println(request.getSession());
//        System.out.println(model.getAttribute("email"));
        System.out.println("email="+email);
//        System.out.println(user);
        user.setEmail(email);
        BaseResponse<User> response=userService.searchByEmail(user);
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