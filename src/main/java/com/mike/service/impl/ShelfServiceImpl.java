package com.mike.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mike.DTO.BaseResponse;
import com.mike.bean.Book;
import com.mike.bean.Shelf;
import com.mike.mapper.BookMapper;
import com.mike.mapper.ShelfMapper;
import com.mike.service.ShelfService;
import com.mike.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/25 19:54
 * @description:
 */

@Service
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    ShelfMapper shelfMapper;
    @Autowired
    BookMapper bookMapper;

    @Override
    public BaseResponse<PageInfo<Book>> showShelf(Shelf shelf,Integer pageNum) {
        BaseResponse<PageInfo<Book>> response=new BaseResponse<>();

        Integer pageSize = 10;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Book> books = shelfMapper.selectByuid(shelf);
            PageInfo<Book> bookPageInfo = new PageInfo<>(books, pageSize);
            System.out.println(bookPageInfo);

            System.out.println("pageinfo="+bookPageInfo.getList());
//                System.out.println(bookPageInfo.getTotal());
            if (books.size()>0){
                response.setOkDaTa("你书架里的书籍查询成功",bookPageInfo);
            }else {
                response.setError("查询书架里的书籍失败！");
            }
        }finally {
            PageHelper.clearPage();
        }
        return response;
    }

    @Override
    public BaseResponse<Shelf> addShelf(Shelf shelf) {
        BaseResponse<Shelf> response=new BaseResponse<>();
        shelf.setId(UUIDUtils.getUUID32());
        shelf.setAddTime(System.currentTimeMillis());
        System.out.println("serverImpl="+shelf);
        //判断插入是否成功
        if (shelfMapper.hasAlready(shelf).size()>0){
            response.setError((bookMapper.selectById(shelf.getBookId())).getName()+":本书已经在你的书架上了，请勿重复添加");
        }else if (shelfMapper.insert(shelf)>0){
            response.setOkDaTa((bookMapper.selectById(shelf.getBookId())).getName()+":书籍添加到书架成功",shelf);
        }else {
            response.setError((bookMapper.selectById(shelf.getBookId())).getName()+":添加书架失败",shelf);
        }

        return response;
    }

    @Override
    public BaseResponse<Shelf> removeShelf(Shelf shelf) {
        BaseResponse<Shelf> response=new BaseResponse<>();
        int i=shelfMapper.delete(shelf);//判断插入是否成功
        response.setOkDaTa("下架书籍成功",shelf);
        return response;
    }
}
