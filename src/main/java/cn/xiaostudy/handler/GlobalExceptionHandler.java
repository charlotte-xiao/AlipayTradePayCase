package cn.xiaostudy.handler;

import cn.xiaostudy.vo.Result;
import com.alipay.api.AlipayApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理支付流程中异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(AlipayApiException.class)
    public Result<?> errorHandler(AlipayApiException e) {
        return Result.fail(e.getMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> errorHandler(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

}
