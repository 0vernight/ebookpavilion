package com.mike.DTO;

import com.mike.bean.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: 23236
 * @date: 2021/5/6 17:58
 * @description:
 * 使用mapstruct的使用
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDto extends Comment {
    private String userImage;
    private String username;
    private String email;

}
