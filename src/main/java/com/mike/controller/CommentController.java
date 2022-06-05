package com.mike.controller;

import com.mike.DTO.BaseResponse;
import com.mike.bean.Book;
import com.mike.bean.Comment;
import com.mike.bean.User;
import com.mike.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 23236
 * @date: 2021/3/15 16:02
 * @description:
 */

@RestController
//    @Controller
public class CommentController {


    @Autowired
    User user;
    @Autowired
    Book book;

    @Autowired
    CommentService commentService;

    @RequestMapping("/addComment")
    public BaseResponse<Comment> addComment(Comment comment,Model model){
        BaseResponse<Comment> response = new BaseResponse<>();
        System.out.println("addcomment ="+comment);

        response=commentService.addCmmt(comment);

        return response;
    }




//    @RequestMapping("/index")
//    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping("/user")
    public User getUser(){
        return user;
    }


    @ResponseBody
    @RequestMapping("/get")
    public Map<String, String[]> getUser(HttpSession session, Model model,
                                      HttpServletRequest request,
                                      HttpServletResponse response
    ){
//    2.public String getUser(HttpSession session){
        System.out.println(session.toString());
//    1.public String getAttibute(@RequestParam("name") String name){
//        System.out.println(name);
        System.out.println(request.getAttribute("input"));
        System.out.println(model.getAttribute("input"));


        Map<String, Object> map = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
//        map.put("model",session);
//        map.put("model",parameterMap);
        System.out.println(request.getHeader("headers"));

        return parameterMap;
    }

}
//    @RequestParam@PathVariable@RequestHeader@ModelAttribute@MatrixVariable@CookieValue@RequestBody
