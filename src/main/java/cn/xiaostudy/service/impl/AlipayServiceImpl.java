package cn.xiaostudy.service.impl;

import cn.xiaostudy.config.AlipayConfig;
import cn.xiaostudy.service.AlipayService;
import cn.xiaostudy.util.CommonUtil;
import cn.xiaostudy.vo.AlipayTradePagePayVO;
import cn.xiaostudy.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static cn.xiaostudy.constant.AlipayConst.PRODUCT_CODE;
import static cn.xiaostudy.constant.AlipayConst.TIMEOUT_EXPRESS;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private AlipayConfig alipayConfig;

    @Override
    public String tradePagePay(AlipayTradePagePayVO alipayTradePagePayVO) throws AlipayApiException {
        //1.构造AlipayTradePagePayModel
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        model.setOutTradeNo(alipayTradePagePayVO.getOutTradeNo());
        // 付款金额，必填
        model.setTotalAmount(String.valueOf(alipayTradePagePayVO.getTotalAmount()));
        // 订单名称，必填
        model.setSubject(alipayTradePagePayVO.getSubject());
        // 商品描述，可空
        model.setBody(alipayTradePagePayVO.getBody());
        // 销售产品码，必填
        model.setProductCode(PRODUCT_CODE);
        // 订单超时时间，可填
        model.setTimeoutExpress(TIMEOUT_EXPRESS);

        //2.设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        alipayRequest.setBizModel(model);

        //3.请求
        AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest);
        if(alipayTradePagePayResponse.isSuccess()){
            return alipayTradePagePayResponse.getBody();
        }else{
            return null;
        }
    }

    @Override
    public Result<?> tradeQuery(AlipayTradeQueryModel alipayTradeQueryModel) throws AlipayApiException {
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        alipayRequest.setBizModel(alipayTradeQueryModel);
        AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayRequest);
        if(alipayTradeQueryResponse.isSuccess()){
            return Result.ok(alipayTradeQueryResponse.getBody());
        }else {
            return Result.fail(alipayTradeQueryResponse.getBody());
        }

    }

    @Override
    public Result<?> tradeRefund(AlipayTradeRefundModel alipayTradeRefundModel) throws AlipayApiException {
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        alipayRequest.setBizModel(alipayTradeRefundModel);
        AlipayTradeRefundResponse alipayTradeRefundResponse = alipayClient.execute(alipayRequest);
        if(alipayTradeRefundResponse.isSuccess()){
            return Result.ok(alipayTradeRefundResponse.getBody());
        }else {
            return Result.fail(alipayTradeRefundResponse.getBody());
        }
    }

    @Override
    public Result<?> tradeRefundQuery(AlipayTradeRefundModel alipayTradeRefundModel) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
        alipayRequest.setBizModel(alipayTradeRefundModel);
        AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse = alipayClient.execute(alipayRequest);
        if(alipayTradeFastpayRefundQueryResponse.isSuccess()){
            return Result.ok(alipayTradeFastpayRefundQueryResponse.getBody());
        }else {
            return Result.fail(alipayTradeFastpayRefundQueryResponse.getBody());
        }
    }

    @Override
    public Result<?> tradeClose(AlipayTradeCloseModel alipayTradeCloseModel) throws AlipayApiException {
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        alipayRequest.setBizModel(alipayTradeCloseModel);
        AlipayTradeCloseResponse alipayTradeCloseResponse = alipayClient.execute(alipayRequest);
        if(alipayTradeCloseResponse.isSuccess()){
            return Result.ok(alipayTradeCloseResponse.getBody());
        }else {
            return Result.fail(alipayTradeCloseResponse.getBody());
        }
    }

    @Override
    public Result<?> billDownload(AlipayDataDataserviceBillDownloadurlQueryModel alipayDataDataserviceBillDownloadurlQueryModel) throws AlipayApiException {
        AlipayDataDataserviceBillDownloadurlQueryRequest alipayRequest =new AlipayDataDataserviceBillDownloadurlQueryRequest();
        alipayRequest.setBizModel(alipayDataDataserviceBillDownloadurlQueryModel);
        AlipayDataDataserviceBillDownloadurlQueryResponse alipayDataDataserviceBillDownloadurlQueryResponse = alipayClient.execute(alipayRequest);
        if(alipayDataDataserviceBillDownloadurlQueryResponse.isSuccess()){
            return Result.ok(alipayDataDataserviceBillDownloadurlQueryResponse.getBody());
        }else {
            return Result.fail(alipayDataDataserviceBillDownloadurlQueryResponse.getBody());
        }
    }

    @Override
    public Result<?> tradeReturn(HttpServletRequest httpServletRequest) {
        Map<String, String> params = CommonUtil.getRequestParams(httpServletRequest);
        // 验证签名
        boolean signVerified = false;
        try{
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        }catch (AlipayApiException e){
            e.printStackTrace();
        }
        if(signVerified){
            Map<String,String> resultHashMap = new HashMap<>(16);
            // new String(params.get("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            resultHashMap.put("商家订单号",params.get("out_trade_no"));
            resultHashMap.put("支付宝订单号",params.get("trade_no"));
            resultHashMap.put("总金额",params.get("total_amount"));
            String result = JSON.toJSONString(params);
            return Result.ok(result);
        }else {
            return Result.fail();
        }
    }

    @Override
    public Result<?> tradeNotify(HttpServletRequest httpServletRequest) {
        Map<String, String> params = CommonUtil.getRequestParams(httpServletRequest);
        // 验证签名
        boolean signVerified = false;
        try{
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        }catch (AlipayApiException e){
            e.printStackTrace();
        }
        if(signVerified){
            Map<String,String> resultHashMap = new HashMap<>(16);
            resultHashMap.put("商户标识",params.get("app_id"));
            resultHashMap.put("商家订单号",params.get("out_trade_no"));
            resultHashMap.put("支付宝订单号",params.get("trade_no"));
            resultHashMap.put("总金额",params.get("total_amount"));
            resultHashMap.put("交易者",params.get("seller_id"));
            resultHashMap.put("交易状态",params.get("trade_status"));
            String result = JSON.toJSONString(params);
            return Result.ok(result);
        }else {
            return Result.fail();
        }
    }


}
