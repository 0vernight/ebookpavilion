package com.mike.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * @author: 23236
 * @date: 2021/3/15 22:28
 * @description:
 */
@Component

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class Forum {
    private String id;
    private String userId;
    private String bookId;
    private String title;
    private String content;
    private String img;
}
