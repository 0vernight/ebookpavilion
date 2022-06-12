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
 * @date: 2021/3/15 18:58
 * @description:
 * 必须表明是容器@Component要不然报错
 */

@Component

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class Book {

    private String id;
    private String type;
    private String name;
    private String author;
    private String brief;
    private String pubyear;
    private String press;
    private String format;
    private String file;
    private String cover;
    private String downloadTimes;
    private int price;
    private int pageNum;
    private int wordNum;
    private String edition;
    private String uploaderId;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date uploadDate;
}
