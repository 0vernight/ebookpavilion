package com.mike.controller;

import com.github.pagehelper.PageInfo;
import com.mike.DTO.BaseResponse;
import com.mike.DTO.CommentDto;
import com.mike.bean.Book;
import com.mike.bean.Comment;
import com.mike.bean.Shelf;
import com.mike.bean.User;
import com.mike.service.BookService;
import com.mike.service.CommentService;
import com.mike.service.ShelfService;
import com.mike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 23236
 * @date: 2021/4/16 18:58
 * @description:
 */


@Controller
public class WebController {

    @Autowired
    User user;
    @Autowired
    Book book;
    @Autowired
    Shelf shelf;
    @Autowired
    Comment comment;

    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    ShelfService shelfService;
    @Autowired
    CommentService commentService;

    //@ResponseBody
    @RequestMapping("/test1")
    public String test1(HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println("进入了测试servlet,返回addtable页面");

        return "pages/main/addtable";
    }
    @RequestMapping({"/"})
    public String index(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "3") int pageSize, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            user = new User();
        }
//        model.addAttribute("bookList", bookService.selectAll().getData());
//        model.addAttribute("bookList", bookService.page(pageNum,pageSize).getItems());
//        pageSize=2;
        model.addAttribute("page", bookService.page(pageNum,pageSize));
        model.addAttribute("pageInfo", bookService.pageInfo(pageNum,pageSize));
        model.addAttribute("user",userService.getUserInfoByID(user).getData());
        return "index";
    }

    @RequestMapping({"/toShelf"})
    public String toShelf(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum) {
        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            model.addAttribute("warnmsg", "没登录不能查看书架，请先登录");
//            return "pages/main/login";
//        } else {
            BaseResponse<PageInfo<Book>> response = shelfService.showShelf(shelf.setUserId(user.getId()),pageNum);

            model.addAttribute("pageInfo",response.getData() );
//            model.addAttribute("bookList",response.getData());
            return "pages/main/shelf";
//        }
    }

    @RequestMapping({"/toSearchBook"})
    public String toSearchBook(String keyword, HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            user = new User();//这个控制器方法里不会用到
        }


        model.addAttribute("bookList", bookService.showByKeyword(keyword).getData());
        model.addAttribute("keyword", keyword);
        model.addAttribute("user",userService.getUserInfoByID(user).getData());
        return "pages/main/search";

    }

    @RequestMapping({"/toAddBook"})
    public String toAddBook(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            model.addAttribute("warnmsg", "你还没等登录，请先登录");
//            return "pages/main/login";
//        } else {
            return "pages/main/addbook";
//        }
    }

    @RequestMapping({"/toBook"})
    public String toBook(HttpServletRequest request, Model model) {
//        System.out.println("toBook look at user="+request.getSession().getAttribute("user"));
//        System.out.println("tobook session id="+request.getSession().getId());
        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            model.addAttribute("warnmsg", "你还没等登录，请先登录");
//            return "pages/main/login";
//        } else {
            BaseResponse<List> response = bookService.selectMyAll(user.getId());
            model.addAttribute("bookList", response.getData());
            return "pages/main/Book";
//        }
    }
    @RequestMapping({"/toDetail"})
    public String toDetail(Book book,HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        List<CommentDto> cmmt=new ArrayList<>();
        if (user==null){
            user=new User();
        }else {
            //        获取评论用户没有登陆就不要查询评论了
            cmmt=commentService.getCommentsByBookId(book.getId());
        }
        System.out.println("user mustbe null="+user);
        System.out.println(book);
//        获取点击的那个书的详细信息
        book=bookService.showByT(book).getData();

//        左侧的推荐栏目使用了模糊查询，把三个集合结合了返回
        List<Book> books=bookService.showByKeyword(book.getName()).getData();
        List<Book> data = bookService.showByKeyword(book.getAuthor()).getData();
        data.forEach(a->{
            if (!books.contains(a)){
                books.add(a);
            }
        });
        List<Book> data1 = bookService.showByKeyword(book.getType()).getData();
        data1.forEach(a->{
            if (!books.contains(a)){
                books.add(a);
            }
        });

        System.out.println("所有的评论="+cmmt);
//        System.out.println("todetail ="+books);
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        model.addAttribute("recommends",books);
        model.addAttribute("comments",cmmt);
        return "pages/main/detail";
    }
    @RequestMapping({"/toEditBook"})
    public String toEditBook(Book book,HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        book=bookService.showByT(book).getData();

        model.addAttribute("user",userService.getUserInfoByID(user).getData());//没有使用
        model.addAttribute("book", book);
        return "pages/main/editbook";
    }

    @RequestMapping("/toRegister")
    public String toRegister(Model model) {
        return "pages/main/registration";
    }

    @RequestMapping({"/toLogin"})
    public String toLogin(User user, HttpServletRequest request,HttpServletResponse response, Model model) {
//        model.addAttribute("user", user);
        model.addAttribute("warnmsg", "请输入邮箱并且输入密码登录！");
        System.out.println("tologin=");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println("进来了cookie");
            for (int i = 0; i < cookies.length; i++) {
                System.out.println("res="+cookies[i].getName()+"="+cookies[i].getValue());
                System.out.println(cookies[i].getValue().isEmpty() );
                System.out.println(cookies[i].getValue() == "");
                if (cookies[i].getName().equals("username")&&cookies[i].getValue() == "") {
                    Cookie cookieUsername = new Cookie("username", null);
//                Cookie cookieEmail = new Cookie("email", null);
//                Cookie cookiePassword = new Cookie("password", null);
//                Cookie rememberme = new Cookie("rememberme", "false");
                    cookieUsername.setMaxAge(60*60*24*7);
//                cookieEmail.setMaxAge(60*60*24*7);
//                cookiePassword.setMaxAge(60*60*24*7);
//                rememberme.setMaxAge(60*60*24*7);
                    response.addCookie(cookieUsername);
//                response.addCookie(cookieEmail);
//                response.addCookie(cookiePassword);
//                response.addCookie(rememberme);
                }

            }

        }

        System.out.println("session="+request.getSession().getAttribute("email")==null);

//        BaseResponse<User> baseResponse = userService.loginByUsernamePass(user);
//        if (baseResponse.getCode()==200) {
//            //创建cookie保存用户名和密码
//            Cookie cookie = new Cookie("username", baseResponse.getData().getUsername());
//            cookie.setMaxAge(60 * 60 * 24 * 7);
//            response.addCookie(cookie);
//        }
        return "pages/main/login";
    }

    @RequestMapping("/toLogout")
    public String toLogout(HttpServletRequest request, Model model, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "4") int pageSize) {
//        删除session当中的这个用户返回首页
        System.out.println("logout=" + request.getSession().getId());
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();      //销毁登录的信息
        model.addAttribute("bookList", bookService.selectAll().getData());
        model.addAttribute("page", bookService.page(pageNum,pageSize));

        return "/index";
    }

    @RequestMapping({"/toProfile"})
    public String toProfile(HttpServletRequest request, Model model) {
//        只是为了获得user里面的userid
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("toprofile->=" + user);
        if (user == null) {
            model.addAttribute("warnmsg", "你还没等登录，请先登录");
            System.out.println("toprofile->tologin");
            return "pages/main/login";
        } else {
            user=userService.getUserInfoByID(user).getData();
            model.addAttribute("user", user);
            return "pages/main/profile";
        }
    }

    @RequestMapping("/toalterProfile")
    public String alterProfile(User user,HttpServletRequest request, Model model) {
//        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",userService.getUserInfoByID(user).getData());
        return "pages/main/alterprofile";
    }

    @RequestMapping("/toChangePwd")
    public String changePassword(HttpServletRequest request, Model model) {
//        User user = (User) request.getSession().getAttribute("user");
        return "pages/main/refactorpwd";
    }

    @RequestMapping("/toRemoveUser")
    public String removeUser(User user, HttpServletRequest request, Model model, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "4") int pageSize) {
        user = (User) request.getSession().getAttribute("user");
//        user.setId("20b3afe0d8db4ea7a212e54f8b7842c8");
        System.out.println("removeUser=" + user);
        userService.removeUser(user);
//        model里面放入baserespose能在前端去得到的吗？
        model.addAttribute("page", bookService.page(pageNum,pageSize));
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping("/toSeting")
    public String toSeting() {

        return "pages/main/seting";
    }

    @RequestMapping({"/socket"})
    public String socket(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "3") int pageSize, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            user = new User();
        }
//        model.addAttribute("bookList", bookService.selectAll().getData());
//        model.addAttribute("bookList", bookService.page(pageNum,pageSize).getItems());
        pageSize=2;
        model.addAttribute("page", bookService.page(pageNum,pageSize));
        model.addAttribute("pageInfo", bookService.pageInfo(pageNum,pageSize));
        model.addAttribute("user",userService.getUserInfoByID(user).getData());
        return "pages/main/websocketclient";
    }
}
