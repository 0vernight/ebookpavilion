package com.mike.service;

import com.mike.DTO.BaseResponse;
import com.mike.bean.Book;
import com.mike.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/4/20 13:41
 * @description:
 */

public interface BookService {
    BaseResponse<List> selectAll();

    BaseResponse<List> selectMyAll(String id);

    BaseResponse<Book> showByT(Book book);

    BaseResponse<List> showByType(String type);

    BaseResponse<List> showByName(String name);

    BaseResponse<List> showByKeyword(String keyword);

    BaseResponse<Book> addBook(User user, Book book, MultipartFile multipartFile,MultipartFile file);

    BaseResponse<Book> alterBook(User user, Book book, MultipartFile coverFile, MultipartFile bookFile);

    BaseResponse<Book> removeBook( Book book);

    BaseResponse<Book> removeShelfBook(Book book);
}
