package com.mike.controller;

import com.mike.DTO.BaseResponse;
import com.mike.bean.Collection;
import com.mike.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/4/20 13:38
 * @description:
 */

@RestController
public class CollectionController {
    @Autowired
    Collection collection;

    @Autowired
    CollectionService collectionService;
    @RequestMapping("/addCollection")
    public BaseResponse<Collection> addCollection(){

        return collectionService.addCollection(collection);
    }
    @RequestMapping("/showCollection")
    public BaseResponse<List> showCollection(){

        return collectionService.showCollection(collection);
    }
    @RequestMapping("/removeCollection")
    public BaseResponse<Collection> removeCollection(){

        return collectionService.removeCollection(collection);
    }
}
