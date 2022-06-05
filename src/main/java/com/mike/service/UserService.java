package com.mike.service;

import com.mike.DTO.BaseResponse;
import com.mike.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author: 23236
 * @date: 2021/3/15 20:10
 * @description:
 */


public interface UserService {
    BaseResponse<List> selectAll();
    BaseResponse<User> loginByUsernamePass(User user);
    BaseResponse<User> register(User user);
    BaseResponse<User> getUserInfoByID(User user);

    BaseResponse<User> alterProfile(User user);

    BaseResponse<User> removeUser(User user);

    BaseResponse<User> refactorPassword(User user,String newPass);

    BaseResponse<User> updateAvatar(User user, MultipartFile file) throws IOException;

    List<User> selectByIds(List<String> ids);
}
