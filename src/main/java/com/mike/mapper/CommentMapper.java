package com.mike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mike.bean.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/25 19:48
 * @description:
 */

@Mapper
//@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    //    @Insert("insert into comment (id,form_id,user_id,book_id,content,comment_img,create_time) values(#{id},#{formId},#{userId},#{bookId},#{content},#{commentImg},#{createTime})")
    void insertCmmt(Comment comment);

    List<Comment> selectAll(Comment comment);

//    List<Comment> seachAllcmmt(String bookId);
}
