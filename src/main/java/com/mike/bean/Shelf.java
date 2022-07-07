package com.mike.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 23236
 * @date: 2021/3/15 18:48
 * @description:
 */
@Component

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class Shelf {
    public String id;
    public String userId;
    public String bookId;
    public Date createTime;
    public Date reviseTime;
    public long addTime;
    public int progress;

}
