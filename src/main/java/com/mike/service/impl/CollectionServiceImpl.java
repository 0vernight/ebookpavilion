package com.mike.service.impl;

import com.mike.DTO.BaseResponse;
import com.mike.bean.Collection;
import com.mike.mapper.CollectionMapper;
import com.mike.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/25 19:55
 * @description:
 */

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;
    @Override
    public BaseResponse<Collection> addCollection(Collection collection) {
        BaseResponse<Collection> response=new BaseResponse<>();
        int i=collectionMapper.insert(collection);//判断插入是否成功
        response.setOkDaTa("收藏书籍成功",collection);
        return response;
    }

    @Override
    public BaseResponse<List> showCollection(Collection collection) {
        BaseResponse<List> response=new BaseResponse<>();
        response.setOkDaTa("查询收藏书籍成功",collectionMapper.selectById(collection));
        return response;
    }

    @Override
    public BaseResponse<Collection> removeCollection(Collection collection) {
        BaseResponse<Collection> response=new BaseResponse<>();
        int i=collectionMapper.deleteCollction(collection);//判断插入是否成功
        response.setOkDaTa("删除收藏书籍成功",collection);
        return response;
    }
}
