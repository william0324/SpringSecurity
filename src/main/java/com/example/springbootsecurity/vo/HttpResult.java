package com.example.springbootsecurity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult {
    /**
     * 状态码常量:
     */
    //成功
    public static final int CODE_OK = 200;
    //业务错误
    public static final int CODE_ERR_BUSINESS = 501;
    //用户未登录
    public static final int CODE_ERR_UNLOGINED = 502;
    //系统错误
    public static final int CODE_ERR_SYS = 503;

    private Integer code;//返回给前端的自定义响应码
    private boolean success;//成功响应为true,失败响应为false
    private String msg;//返回给前端的消息
    private Object data;//返回给前端的数据
    //成功响应的方法 -- 返回的HttpResult中只封装了成功状态码
    public static HttpResult ok(){
        return new HttpResult(CODE_OK,true,null, null);
    }
    //成功响应的方法 -- 返回的HttpResult中封装了成功状态码和响应信息
    public static HttpResult ok(String message){
        return new HttpResult(CODE_OK,true,message, null);
    }
    //成功响应的方法 -- 返回的HttpResult中封装了成功状态码和响应数据
    public static HttpResult ok(Object data){
        return new HttpResult(CODE_OK,true,null, data);
    }
    //成功响应的方法 -- 返回的HttpResult中封装了成功状态码和响应信息和响应数据
    public static HttpResult ok(String message, Object data){
        return new HttpResult(CODE_OK,true,message, data);
    }
    //失败响应的方法 -- 返回的HttpResult中封装了失败状态码和响应信息
    public static HttpResult err(int errCode, String message){
        return new HttpResult(errCode,false, message, null);
    }
    //失败响应的方法 -- 返回的HttpResult中封装了失败状态码和响应信息和响应数据
    public static HttpResult err(int errCode,String message,Object data){
        return new HttpResult(errCode,false,message, data);
    }
}
