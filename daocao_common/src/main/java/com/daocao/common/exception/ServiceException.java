package com.daocao.common.exception;
/*
自定义的业务异常
 */
public class ServiceException extends RuntimeException{

    public static final Long serialVersionUID=1L;

    /*
    错误码
     */
    private Integer code;

    /*
    错误提示
     */
    private String message;

    /*
    错误明细，内部调试错误
     */
    private String detailMessage;

    /*
    空构造方法，避免反序列化问题
     */
    public ServiceException(){

    }

    public ServiceException(String message){
        this.message=message;
    }
    public ServiceException(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public String getDetailMessage(){
        return  detailMessage;
    }
    public String getMessage(){
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }
}
