package com.mike.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 23236
 * @date: 2021/3/15 22:27
 * @description:
 */
@Component

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
//实体类与数据库表的名字不一致时可以通过一下的注解更改，或者在配置里global-config： table-prefix：t
//@TableName("tcommet")

public class Comment {
//    @TableId(value = "uid",type = IdType.AUTO)
    private String id;
    private String formId;
    private String userId;
    private String bookId;
    private String content;
    private String commentImg;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
}
