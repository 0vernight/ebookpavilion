package com.mike.service.impl;

import com.mike.DTO.BaseResponse;
import com.mike.bean.User;
import com.mike.mapper.UserMapper;
import com.mike.service.UserService;
import com.mike.utils.JudgeFormat;
import com.mike.utils.MD5Utils;
import com.mike.utils.UUIDUtils;
import com.mike.utils.foperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author: 23236
 * @date: 2021/3/15 20:11
 * @description:
 */

@SuppressWarnings("ALL")
@Service
public class UserServiceImpl implements UserService {

    //真正操作数据库的接口相当于jdbc 的userDaoImpl类一样的功能
    @Autowired
    UserMapper userMapper;

    @Autowired
    User user;

//        想要判断前端的返回来的数据没有成功
    JudgeFormat judgeFormat;
    @Override
    public BaseResponse<List> selectAll() {
        BaseResponse<List> response=new BaseResponse<>();
        List<User> users = userMapper.selectAll();
        response.setCode(200).setMessage("success!:")
                .setData(users);
        return response;
    }

    @Override
    public BaseResponse<User> loginByUsernamePass(User user) {
        BaseResponse<User> response=new BaseResponse<>();
        judgeFormat=new JudgeFormat(user);
        if (judgeFormat.judegeUser()) {
            user.setPassword(MD5Utils.stringToMD5(user.getPassword()));
            User reuser = userMapper.selectByUsernamePass(user.getEmail(),user.getPassword());
//            System.out.println("登录之前的查询="+reuser);
            if (reuser!=null){
                response.setCode(200)
                        .setMessage("登录成功")
                        .setData(reuser);
            }else {
                response.setCode(201)
                        .setMessage("账号或密码错误");
            }
        }else {
            response.setError("输入有误！");
        }

        return response;
    }

    @Override
    public BaseResponse<User> register(User user) {
        BaseResponse<User> response=new BaseResponse<>();
        judgeFormat=new JudgeFormat(user);
        if (judgeFormat.judegeUser()) {
            user.setId(UUIDUtils.getUUID32());
            if ((userMapper.selectById(user)==null)){
//                System.out.println("没有数据库中才加入");
//               这里默认设置一个昵称
                user.setNickname(user.getUsername().substring(0,2)+"@booker");
//               取出用户密码，通过md5加密了以后再放进去
                user.setPassword(MD5Utils.stringToMD5(user.getPassword()));
                user.setCreationDate(System.currentTimeMillis());

                System.out.println("register result="+userMapper.register(user));

                response.setCode(200).setMessage("注册成功！").setData(user);
            }else {
//                System.out.println("说明已经在数据库当中了");
                response.setCode(201).setMessage("注册失败！原因已经存在数据库当中了，请直接登录").setData(user);
            }
        }else {
//            System.out.println("输入有误");
            response.setCode(202).setMessage("你输入有误，请重新输入").setData(user);
        }
        return response;
    }

    @Override
    public BaseResponse<User> getUserInfoByID(User user) {
        BaseResponse<User> response=new BaseResponse<>();

        response.setOkDaTa("用户数据",userMapper.selectById(user));
        return response;
    }


    @Override
    public BaseResponse<User> alterProfile(User user) {
        BaseResponse<User> response=new BaseResponse<>();
        if (userMapper.updateById(user)==1){
            response.setOkDaTa("个人数据更新成功",user);
        }else {
            response.setError("更新个人数据失败",user);
        }
//        System.out.println("after update="+);//
        return response;
    }

    @Override
    public BaseResponse<User> updateAvatar(User user, MultipartFile multipartFile) throws IOException {
        BaseResponse<User> response=new BaseResponse<>();
//        把文件，格式，路径传给上传器，上传完了以后，返回相对路径
        List<String> imageType = new ArrayList<String>(Arrays.asList("jpg","jpeg", "png", "bmp", "gif"));
        String path="uploadImg";
        String avatar= foperation.uploadImg(multipartFile);
        if (avatar==null){
            response.setError("头像上传失败",user);
        }else {
            user.setAvatar(avatar);
            if (userMapper.updateAvatarById(user)!=1){
                response.setError("头像添加数据库失败");
            }
            response.setOkDaTa("头像更新成功",user);
        }
//        System.out.println(user);
//        System.out.println("after update avatar="+userMapper.updateAvatarById(user));//成功了返回1，失败了0
        return response;
    }

    @Override
    public List<User> selectByIds(List<String> ids) {
        List<User> users= userMapper.selectByIds(ids);
        return users;
    }

    @Override
    public BaseResponse<User> searchByName(User user) {
        BaseResponse<User> response=new BaseResponse<>();

//        System.out.println(userMapper.selectByname(user));
        if (userMapper.selectByname(user) == null) {
            response.setError("查无此人");
        }else {
            response.setOkDaTa("用户数据", userMapper.selectByname(user));
        }
        return response;
    }

    @Override
    public BaseResponse<User> searchByEmail(User user) {
        BaseResponse<User> response=new BaseResponse<>();

//        System.out.println(userMapper.selectByname(user));
        if (userMapper.searchByEmail(user) == null) {
            response.setError("查无此人");
        }else {
            response.setOkDaTa("用户数据", userMapper.searchByEmail(user));
        }
        return response;
    }

    @Override
    public BaseResponse<User> removeUser(User user) {
        BaseResponse<User> response=new BaseResponse<>();

        System.out.println("after deleteuser="+userMapper.deleteById(user));//
        response.setOkDaTa("删除用户数据",user);
        return response;
    }

    @Override
    public BaseResponse<User> refactorPassword(User user,String newPass) {
        BaseResponse<User> response=new BaseResponse<>();
        //新的密码转为md5后进行更新
        newPass=MD5Utils.stringToMD5(newPass);
        System.out.println("after refactorPassword="+userMapper.updatePassword(user,newPass));//成功了返回1，失败了0
        response.setOkDaTa("更改用户密码成功",user);
        return response;
    }




}