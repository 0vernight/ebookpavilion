package com.mike.DTO;

import com.mike.bean.Book;
import com.mike.bean.Comment;
import com.mike.bean.Shelf;
import com.mike.bean.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author: 23236
 * @date: 2021/3/15 19:08
 * @description:
 */

@Mapper
public interface ItemConverter {

    ItemConverter INSTANCE= Mappers.getMapper(ItemConverter.class);

    @Mapping(source = "book.id", target = "id")
    BookDTO toBookDTO(Book book, Shelf shelf);

//    Mapping是为了添加不一样属性
    @Mapping(source = "user.avatar", target = "userImage")
    @Mapping(source = "comment.id",target ="id")
    CommentDto commentToDto(Comment comment, User user);


}
