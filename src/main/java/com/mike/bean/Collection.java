package com.mike.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

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
public class Collection {
    private String id;
    private String userId;
    private String bookId;
    private Long collectTime;
}
