package com.mike.controller;

import com.mike.DTO.BaseResponse;
import com.mike.bean.Book;
import com.mike.bean.User;
import com.mike.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: 23236
 * @date: 2021/4/20 13:37
 * @description:
 */

    @RestController
public class BookController {

    @Autowired
    private Book book;

    @Autowired
    private User user;
    @Autowired
    public BookController(Book book, User user) {
        this.book = book;
        this.user = user;
    }
    @Autowired
    BookService bookService;
    @CrossOrigin
    @RequestMapping("/showAllBook")
    public BaseResponse<List> showAllBook(){

        return bookService.selectAll();
    }
    @RequestMapping("/showByType")
    public BaseResponse<List> showByType(){
//        user.setId("ba94f43a2ece46568629093e6ea7ad9a");
//        book.setType("科幻");
        return bookService.showByType(book.getType());
    }
    @RequestMapping("/showByName")
    public BaseResponse<List> showByName(){
//        user.setId("ba94f43a2ece46568629093e6ea7ad9a");
//        book.setType("科幻");
//        book.setName("和");
        return bookService.showByName(book.getName());
    }
    @RequestMapping("/showMyBook")
    public BaseResponse<List> showMyAllBook(){
        user.setId("ba94f43a2ece46568629093e6ea7ad9a");
        return bookService.selectMyAll(user.getId());
    }

    @RequestMapping("/addBook")
    public BaseResponse<Book> addBook(Book book,@RequestParam("coverfile") MultipartFile coverFile, @RequestParam("bookfile") MultipartFile bookFile, HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");

//        user.setId("78c6fbdcf468414580d8e3157bf875dc");
        System.out.println("addbook="+user);
        System.out.println("addBook=" + book);
        System.out.println("file=" +coverFile.getOriginalFilename()+ bookFile.getOriginalFilename());

        return bookService.addBook(user,book,coverFile,bookFile);
    }
    @RequestMapping("/alterBook")
    public BaseResponse<Book> alterBook(Book book, HttpServletRequest request, Model model,@RequestParam("coverfile") MultipartFile coverFile, @RequestParam("bookfile") MultipartFile bookFile){
        User user = (User) request.getSession().getAttribute("user");

//        System.out.println("addbook="+user);
        System.out.println("addBook=" + book+coverFile.isEmpty()+bookFile.getOriginalFilename());
        return bookService.alterBook(user,book,coverFile,bookFile);
    }
    @RequestMapping("/removeBook")
    public BaseResponse<Book> removeBook(Book book, HttpServletRequest request, Model model){

        return bookService.removeBook(book);
    }@RequestMapping("/removeShelfBook")
    public BaseResponse<Book> removeShelfBook(Book book, HttpServletRequest request, Model model){

        return bookService.removeShelfBook(book);
    }
}
