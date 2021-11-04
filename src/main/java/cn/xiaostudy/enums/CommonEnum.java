package cn.xiaostudy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description
 */
@Getter
@AllArgsConstructor
public enum CommonEnum {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    FAIL(500, "操作失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;
}
