package com.mike.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author: 23236
 * @date: 2021/3/15 21:52
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class BaseResponse<T> {
    private int code;
    private String message;
    private Long  total;
    private int nextPage;
    private T data;

    public BaseResponse<T> setOkDaTa(String msg,T data){
        this.code=200;
        this.message=msg;
        this.setData(data);
        return this;
    }

    public BaseResponse<T> setOkDaTa(T data){
        this.code=200;
        this.message="成功";
        this.setData(data);
        return this;
    }
    public BaseResponse<T> setError(String msg){
        this.code=201;
        this.message=msg;
        return this;
    }
    public BaseResponse<T> setError(String msg,T data){
        this.code=201;
        this.message=msg;
        this.data=data;
        return this;
    }
}
