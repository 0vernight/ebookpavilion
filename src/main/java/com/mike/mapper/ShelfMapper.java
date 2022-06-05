package com.mike.mapper;

import com.mike.bean.Book;
import com.mike.bean.Shelf;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/25 19:46
 * @description:
 */

@Mapper
public interface ShelfMapper {
    @Select("select * from ebp.book where id in(select book_id from ebp.shelf where user_id=#{userId})")
    List<Book> selectByuid(Shelf shelf);

    @Insert("insert into shelf (id,user_id,book_id,add_time) values(#{id},#{userId},#{bookId},#{addTime})")
    int insert(Shelf shelf);

    @Select("select * from shelf where user_id=#{userId} and book_id=#{bookId}")
    List<Shelf> hasAlready(Shelf shelf);

    @Delete("delete from shelf where book_id=#{bookId}")
    int delete(Shelf shelf);
}
