package cn.xiaostudy.service;

import cn.xiaostudy.vo.AlipayTradePagePayVO;
import cn.xiaostudy.vo.Result;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description Alipay服务
 */
public interface AlipayService {

    /**
     * 支付操作
     * @param alipayTradePagePayVO 订单信息
     * @return 响应HTML
     */
    String tradePagePay(AlipayTradePagePayVO alipayTradePagePayVO) throws AlipayApiException;


    /**
     * 支付成功同步回调
     * @param httpServletRequest 请求Servlet
     * @return 响应HTML
     */
    Result<?> tradeReturn(HttpServletRequest httpServletRequest);

    /**
     * 支付成功异步回调
     * @param httpServletRequest 请求Servlet
     * @return 响应HTML
     */
    Result<?> tradeNotify(HttpServletRequest httpServletRequest);

    /**
     * 订单查询
     * @param alipayTradeQueryModel 订单查询模型
     * @return: 查询结果
     */
    Result<?> tradeQuery(AlipayTradeQueryModel alipayTradeQueryModel) throws AlipayApiException;

    /**
     * 退款
     * @param alipayTradeRefundModel 退款订单模型
     * @return 退款结果
     */
    Result<?> tradeRefund(AlipayTradeRefundModel alipayTradeRefundModel) throws AlipayApiException;

    /**
     * 退款订单查询
     * @param alipayTradeRefundModel 退款订单模型
     * @return 退款结果
     */
    Result<?> tradeRefundQuery(AlipayTradeRefundModel alipayTradeRefundModel) throws AlipayApiException;

    /**
     * 关闭订单
     * @param alipayTradeCloseModel 关闭订单模型
     * @return 关闭结果
     */
    Result<?> tradeClose(AlipayTradeCloseModel alipayTradeCloseModel) throws AlipayApiException;

    /**
     * 账单下载
     * @param alipayDataDataserviceBillDownloadurlQueryModel 账单下载模型
     * @return 下载结果
     */
    Result<?> billDownload(AlipayDataDataserviceBillDownloadurlQueryModel alipayDataDataserviceBillDownloadurlQueryModel) throws AlipayApiException;
}
