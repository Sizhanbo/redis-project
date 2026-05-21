package com.itszb.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//封装统一响应结果
public class Result {
    private Integer code;//响应状态码: 1表示成功,0表示失败
    private String msg;//响应消息: success表示成功,error表示失败
    private Object data;//响应的具体数据

    //针对查询成功返回的结果对象
    public static Result success(Object data) {
        return new Result(1,"success",data);
    }
    //针对增删改成功返回的结果对象(不需要返回具体数据,响应的固定的字符串消息success)
    public static Result success() {
        return new Result(1,"success",null);
    }

    //针对增删改成功返回的结果对象(不需要返回具体数据,响应的自定义的字符串消息)
    public static Result success(String msg) {
        return new Result(1,msg,null);
    }

    //针对增删改查失败返回的结果对象(不需要返回具体数据,响应的固定的字符串消息error)
    public static Result error() {
        return new Result(0,"error",null);
    }

    //针对增删改查失败返回的结果对象(不需要返回具体数据,响应的自定义的字符串消息)
    public static Result error(String msg) {
        return new Result(0,msg,null);
    }
}