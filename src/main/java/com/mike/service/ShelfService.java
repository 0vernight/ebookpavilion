package com.mike.service;

import com.github.pagehelper.PageInfo;
import com.mike.DTO.BaseResponse;
import com.mike.bean.Book;
import com.mike.bean.Shelf;

/**
 * @author: 23236
 * @date: 2021/3/25 19:50
 * @description:
 */


public interface ShelfService {
    BaseResponse<PageInfo<Book>> showShelf(Shelf shelf,Integer pageNum);

    BaseResponse<Shelf> addShelf(Shelf shelf);

    BaseResponse<Shelf> removeShelf(Shelf shelf);
}
