package com.mike.service;

import com.mike.DTO.BaseResponse;
import com.mike.DTO.CommentDto;
import com.mike.bean.Comment;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/25 19:52
 * @description:
 */


public interface CommentService {
    BaseResponse<Comment> addCmmt(Comment comment);

    List<CommentDto> getCommentsByBookId(String bookId);
}
