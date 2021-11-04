package cn.xiaostudy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description 统一收单交易支付接口请求参数
 * 具体信息请访问: https://opendocs.alipay.com/apis/api_1/alipay.trade.page.pay
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlipayTradePagePayVO {

    /**
     * 商户订单号
     */
    private String outTradeNo;


    /**
     * 订单总金额
     * 单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     */
    private Double totalAmount;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 订单描述
     */
    private String body;

}
