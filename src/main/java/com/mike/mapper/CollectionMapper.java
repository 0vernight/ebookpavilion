package com.mike.mapper;

import com.mike.bean.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/25 19:47
 * @description:
 */

@Mapper
public interface CollectionMapper {

    @Insert("insert into collection ")
    int insert(Collection collection);

    @Select("select from collection where id=#{id}")
    List<Collection> selectById(Collection collection);

    @Delete("delete from collection where id=#{id}")
    int deleteCollction(Collection collection);
}
