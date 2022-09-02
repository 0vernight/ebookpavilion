package com.mike.bean;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 23236
 * @createTime: 2022/7/9   23:08
 * @description: 分页也是很重要的
 **/

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
//@Accessors(chain = true)
@ConfigurationProperties(prefix = "page")
public class Page<T> {
    public static final Integer PAGE_SIZE=4;
    public Integer pageNum;
    public Integer totalPage;
    public Integer pageSize=PAGE_SIZE;
    public Integer totalCont;
    public List<T> list;
    @Value(value = "metaCoin")
    String  meta;
}
