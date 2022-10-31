package com.mike.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mike.DTO.BaseResponse;
import com.mike.DTO.CommentDto;
import com.mike.DTO.ItemConverter;
import com.mike.bean.Comment;
import com.mike.bean.User;
import com.mike.mapper.CommentMapper;
import com.mike.service.CommentService;
import com.mike.service.UserService;
import com.mike.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author: 23236
 * @date: 2021/3/25 19:56
 * @description:
 */

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService {
    @Autowired
    Comment comment;

    @Autowired
    UserService userService;
    @Autowired
    CommentMapper commentMapper;

    ItemConverter itemConverter = ItemConverter.INSTANCE;

    @Override
    public BaseResponse<Comment> addCmmt(Comment comment) {
        BaseResponse<Comment> response = new BaseResponse<>();
//        不需要转换了,如果从前端过来的那么必须用lombok的@datetimeformat来转换
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String fd=simpleDateFormat.format(date.getTime());
        comment.setId(UUIDUtils.getUUID32());

        System.out.println(comment);
        if (comment.getContent() != null) {
            commentMapper.insertCmmt(comment);
            response.setOkDaTa("评论成功！", comment);
        } else {
            response.setError("评论空，失败!");
        }
        return response;
    }

    @Override
    public List<CommentDto> getCommentsByBookId(String bookId) {
        List<Comment> comments = commentMapper.selectAll(comment.setBookId(bookId));
        List<String> ids = comments.stream().map(Comment::getUserId).collect(Collectors.toList());
        List<User> userList = userService.selectByIds(ids);
        Map<String, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
//        跟上面的同等
//        Map<String,User> userMap1=new HashMap<>();
//        userList.forEach(user->{
//            userMap1.put(user.getId(),user);
//        });

        List<CommentDto> commentDtos = new ArrayList<>();
        comments.forEach(cmmt -> {
            if (userMap.containsKey(cmmt.getUserId())) {
                CommentDto commentDto = itemConverter.commentToDto(cmmt, userMap.get(cmmt.getUserId()));
                commentDtos.add(commentDto);
            }
//            userList.forEach(usl->{
//                if (cmmt.getUserId()==usl.getId()){
//                    commentDtos.add(ItemConverter.INSTANCE.commentToDto(cmmt,usl));
//                }
//            });

        });

        return commentDtos;
    }

}
//        comments.forEach(item->{
//            ids.add(item.getUserId());
//        });