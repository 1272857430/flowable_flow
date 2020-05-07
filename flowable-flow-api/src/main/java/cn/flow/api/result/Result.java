package cn.flow.api.result;

import lombok.Getter;

@Getter
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result() {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
    }

    public Result(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public Result(ResultCode resultCode, T data){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}