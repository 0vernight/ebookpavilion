package com.mike.DTO;

import com.mike.bean.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: 23236
 * @date: 2021/3/15 18:59
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO extends Book {
    private long addTime;
    private int progress;
}
