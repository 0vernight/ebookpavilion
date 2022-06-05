package com.mike.controller;

import com.github.pagehelper.PageInfo;
import com.mike.DTO.BaseResponse;
import com.mike.bean.Book;
import com.mike.bean.Shelf;
import com.mike.bean.User;
import com.mike.service.BookService;
import com.mike.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 23236
 * @date: 2021/3/20 13:37
 * @description:
 */

@RestController
@RequestMapping("shelf")
public class ShelfController {
    @Autowired
    Shelf shelf;
    @Autowired
    ShelfService shelfService;
    @Autowired
    BookService bookService;

    @Autowired
    User user;
    @Autowired
    Book book;

    @RequestMapping("show")
    public BaseResponse<PageInfo<Book>> showShelf(){

        return shelfService.showShelf(shelf,1);
    }
    @RequestMapping("add")
    public BaseResponse<Shelf> addShelf(Book book, HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        BaseResponse<Shelf> response = new BaseResponse<>();
        if (user==null){
            model.addAttribute("warnmsg","你还没等登录，添加书籍需先登录!");
            response.setCode(202).setMessage("你还没等登录，添加书籍需先登录!");
        }else {
            shelf.setUserId(user.getId());
            shelf.setBookId(book.getId());

            response=shelfService.addShelf(shelf);
        }
        System.out.println("shelf add="+response.getData());
        return response;
    }

    @RequestMapping("remove")
    public BaseResponse<Shelf> removeShelf(User user, Book book){

        return shelfService.removeShelf(shelf);
    }

}
