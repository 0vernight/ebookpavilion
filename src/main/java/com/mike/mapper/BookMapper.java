package com.mike.mapper;

import com.mike.bean.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/4/20 13:40
 * @description:
 */
@Mapper
public interface BookMapper {

    @Select("select * from book limit 10")
    List<Book> selectAllBook();

    @Select("select * from book where uploader_id=#{uploaderId}")
    List<Book> selectAllBookByUserId(@Param("uploaderId") String id);

    @Select("select * from book where id=#{id}")
    Book selectById(String id);

    @Select("select * from book where type=#{type}")
    List<Book> selectByType(String type);

    @Select("select * from book where name like concat('%',#{name},'%')")
    List<Book> selectByName(String name);

    @Select("select * from book where name like concat('%',#{keyword},'%') or type like concat('%',#{keyword},'%') or author like concat('%',#{keyword},'%') ")
    List<Book> selectByKeyword(String keyword);

    @Select("select * from book where id=#{id}")
    Book selectBook(Book book);

    @Insert("insert into book (id,type,name,author,brief,pubyear,press,up_date,format,file,cover,page_num,word_num,uploader_id) values(#{id},#{type},#{name},#{author},#{brief},#{pubyear},#{press},#{upDate},#{format},#{file},#{cover},#{pageNum},#{wordNum},#{uploaderId})")
    Long insertBook(Book book);

    @Select("select * from book where type=#{type} and name=#{name} and author=#{author} and format=#{format} and edition=#{edition}")
    List<Book> hasInsert(Book book);

    @Update("update book set type=#{type} , name=#{name} , author=#{author} , brief=#{brief} , pubyear=#{pubyear} , press=#{press} , format=#{format} , file=#{file} , cover=#{cover} , page_num=#{pageNum} , word_num=#{wordNum} , edition=#{edition} where id=#{id}")
    int updateBook(Book book);

    @Delete("delete from book where id=#{id} ")
    int deleteBook(Book book);
}
