package cn.xiaostudy.vo;

import lombok.Data;
import static cn.xiaostudy.enums.CommonEnum.*;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description JSON返回数据
 */
@Data
public class Result<T> {

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> Result<T> ok() {
        return restResult( null, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static <T> Result<T> ok(String message) {
        return restResult(null,SUCCESS.getCode(), message);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static <T> Result<T> ok(T data, String message) {
        return restResult( data, SUCCESS.getCode(), message);
    }

    public static <T> Result<T> fail() {
        return restResult( null, FAIL.getCode(), FAIL.getDesc());
    }

    public static <T> Result<T> fail(String message) {
        return restResult(null,FAIL.getCode(), message);
    }

    public static <T> Result<T> fail(T data) {
        return restResult( data, FAIL.getCode(), FAIL.getDesc());
    }

    public static <T> Result<T> fail(T data, String message) {
        return restResult( data, FAIL.getCode(), message);
    }


    private static <T> Result<T> restResult( T data, Integer code, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setData(data);
        return apiResult;
    }

}

