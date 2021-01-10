package com.uvideo.miaosha.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseVo<T>{

    private int code;
    private String msg;
    private T data;

    public ResponseVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public <T> ResponseVo<T> success(T data){
        return new ResponseVo(200,"success",data);
    }

    public ResponseVo success(){
        return new ResponseVo(200,"success",null);
    }

    public ResponseVo error(T data){
        return new ResponseVo(500,"error",data);
    }

    public ResponseVo error(){
        return new ResponseVo(500,"error",null);
    }
}
