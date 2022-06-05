package com.mike.service.impl;

import com.mike.DTO.BaseResponse;
import com.mike.bean.Book;
import com.mike.bean.Shelf;
import com.mike.bean.User;
import com.mike.mapper.BookMapper;
import com.mike.mapper.ShelfMapper;
import com.mike.service.BookService;
import com.mike.utils.UUIDUtils;
import com.mike.utils.foperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author: 23236
 * @date: 2021/4/20 14:05
 * @description:
 */

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    Book book;
    @Autowired
    Shelf shelf;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    ShelfMapper shelfMapper;

    @Override
    public BaseResponse<List> selectAll() {
        BaseResponse<List> response = new BaseResponse<>();
        List<Book> books = bookMapper.selectAllBook();

        response.setOkDaTa("查询所有的书成功了", books);
        return response;
    }

    @Override
    public BaseResponse<List> selectMyAll(String id) {

        BaseResponse<List> response = new BaseResponse<>();
        response.setOkDaTa("查询我所有的书成功了", bookMapper.selectAllBookByUserId(id));
        return response;
    }

    @Override
    public BaseResponse<Book> showByT(Book book) {
        BaseResponse<Book> response = new BaseResponse<>();
        response.setOkDaTa("查询我所有的书成功了", bookMapper.selectBook(book));
        return response;
    }

    @Override
    public BaseResponse<List> showByType(String type) {
        BaseResponse<List> response = new BaseResponse<>();
        response.setOkDaTa("查询按类型的书成功了", bookMapper.selectByType(type));
        return response;
    }

    @Override
    public BaseResponse<List> showByName(String name) {
        BaseResponse<List> response = new BaseResponse<>();
        response.setOkDaTa("查询按名字的所有的书成功了", bookMapper.selectByName(name));
        return response;
    }

    @Override
    public BaseResponse<List> showByKeyword(String keyword) {
        BaseResponse<List> response = new BaseResponse<>();
        response.setOkDaTa("按关键字查询成功了", bookMapper.selectByKeyword(keyword));
        return response;
    }

    @Override
    public BaseResponse<Book> addBook(User user, Book book, MultipartFile coverFile, MultipartFile bookFile) {
        BaseResponse<Book> response = new BaseResponse<>();
        book.setId(UUIDUtils.getUUID32());
        book.setUpDate(new Date());
        book.setUploaderId(user.getId());
        if (book.getName().isEmpty()|| book.getType().isEmpty()||book.getAuthor().isEmpty()||book.getBrief().isEmpty()||book.getPubyear().isEmpty()||book.getPress().isEmpty()){
            response.setError("上传书籍时所有的字段不能为空", book);
        }else if (!coverFile.isEmpty() && !bookFile.isEmpty()) {
            String cover = foperation.uploadImg(coverFile);
            String file = foperation.uplodBook(bookFile);
//        判断用户要插入的数据如果在数据库里那么不让
//        System.out.println(bookMapper.hasInsert(book).size());
            if (cover == null || file == null) {
                response.setError("上传书籍文件时出错了", book);
            } else if (bookMapper.hasInsert(book).size() > 0) {
                response.setError("这本书已经存在了请查看", bookMapper.hasInsert(book).get(0));
            } else {
                book.setCover(cover);
                book.setFile(file);
//            插入时更改书籍的格式
                book.setFormat(file.substring(file.lastIndexOf(".") + 1));
                bookMapper.insertBook(book);
                response.setOkDaTa("添加书籍成功了", book);
            }
        }else {
            response.setError("上传的书籍文件和封面不能为空出错了", book);
        }
        return response;
    }

    @Override
    public BaseResponse<Book> alterBook(User user, Book book, MultipartFile coverFile, MultipartFile bookFile) {
        BaseResponse<Book> response = new BaseResponse<>();
//        判断这本书籍是不是上传者本人在进行修改操作
        System.out.println("befor update=" + book);
        if (!coverFile.isEmpty()) {
            String cover = foperation.uploadImg(coverFile);
            book.setCover(cover);
        }
        if (!bookFile.isEmpty()) {
            String file = foperation.uplodBook(bookFile);
            book.setFile(file);
        }
        if (bookMapper.updateBook(book) > 0) {
            response.setOkDaTa("修改成功了", book);
        } else {
            response.setError("修改失败了！");
        }
//        System.out.println("after update="+);
        return response;
    }

    @Override
    public BaseResponse<Book> removeBook(Book book) {
        BaseResponse<Book> response = new BaseResponse<>();

        if (bookMapper.deleteBook(book) > 0) {
            response.setOkDaTa("删除成功了", book);
        } else {
            response.setError("删除失败");
        }
        System.out.println("after delete=");


        return response;
    }

    @Override
    public BaseResponse<Book> removeShelfBook(Book book) {
        BaseResponse<Book> response = new BaseResponse<>();
        shelf.setBookId(book.getId());
        if (shelfMapper.delete(shelf) > 0) {
            response.setOkDaTa("删除成功了", book);
        } else {
            response.setError("删除失败");
        }
        System.out.println("after delete=");


        return response;
    }
}
