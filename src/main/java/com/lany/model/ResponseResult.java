package com.lany.model;

import com.lany.config.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义返回结果
 */
@Data
@AllArgsConstructor
public class ResponseResult {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object data;

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ResponseResult(ResultStatus status, Object data) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public static ResponseResult ok(Object data) {
        return new ResponseResult(ResultStatus.SUCCESS, data);
    }

    public static ResponseResult ok() {
        return new ResponseResult(ResultStatus.SUCCESS);
    }

    public static ResponseResult error(ResultStatus error) {
        return new ResponseResult(error);
    }

}
