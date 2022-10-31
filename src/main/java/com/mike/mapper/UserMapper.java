package com.mike.mapper;

import com.mike.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/15 19:14
 * @description:
 */

@Mapper
public interface UserMapper {

//    这个在xml配置文件里面写的。
//    @Select("Select * from user")
    List<User> selectAll();

    List<User> test1(@Param("ids") List<String> ids);

    @Select("Select * from user where 1=1 and username=#{username}   and password = #{password}")
    User selectByUsernamePass(User user);
    @Select("Select * from user where 1=1 and  email = #{email}  and password = #{password}")
    User selectByEmailPass(User user);

    @Select("Select * from user where username = #{username}")
    List<User> selectByUsername(@Param("username") String username);

    @Insert("insert into user (id, username,nickname, password, email, phone, age, sex, creation_date, birthday, address) values(#{id}, #{username},#{nickname}, #{password}, #{email}, #{phone}, #{age}, #{sex}, #{creationDate}, #{birthDay}, #{address})")
    Long register(User user);

    @Select("Select * from user where id = #{id}")
    User selectById(User user);

    @Update("update user set nickname=#{nickname}, phone=#{phone}, age=#{age}, sex=#{sex}, birth_day=#{birthDay}, address=#{address} where id=#{id}")
    int updateById(User user);

    @Update("update user set password=#{newPass} where id=#{user.id} and password=#{user.password}")
    int updatePassword(User user,String newPass);

    @Update("update user set avatar=#{avatar} where id=#{id}")
    int updateAvatarById(User user);

    @Delete("delete from user where id=#{id}")
    int deleteById(User user);

    List<User> selectByIds(List<String> ids);

    @Select("select *  from user where username=#{username}")
    List<User> selectByname(User user);

    @Select("select *  from user where email=#{email}")
    User searchByEmail(User user);

}
