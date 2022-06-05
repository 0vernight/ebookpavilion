package com.mike.service;

import com.mike.DTO.BaseResponse;
import com.mike.bean.Collection;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/25 19:51
 * @description:
 */


public interface CollectionService {
    BaseResponse<Collection> addCollection(Collection collection);

    BaseResponse<List> showCollection(Collection collection);

    BaseResponse<Collection> removeCollection(Collection collection);
}
