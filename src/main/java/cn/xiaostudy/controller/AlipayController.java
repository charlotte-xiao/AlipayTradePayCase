package cn.xiaostudy.controller;

import cn.xiaostudy.service.AlipayService;
import cn.xiaostudy.vo.AlipayTradePagePayVO;
import cn.xiaostudy.vo.Result;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static cn.xiaostudy.constant.AlipayConst.HTML_CONTENT_TYPE;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description 支付宝电脑网站支付类
 */
@Controller
public class AlipayController {

    @Resource
    private AlipayService alipayService;

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    /**
     * 支付操作
     */
    @PostMapping("/tradePagePay")
    public void tradePagePay(AlipayTradePagePayVO alipayTradePagePayVO, HttpServletResponse response) throws AlipayApiException{
        response.setContentType(HTML_CONTENT_TYPE);
        try(Writer writer = response.getWriter()){
            writer.write(alipayService.tradePagePay(alipayTradePagePayVO));
            writer.flush();
        }catch  (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单查询
     */
    @PostMapping("/tradeQuery")
    @ResponseBody
    public Result<?> tradeQuery(AlipayTradeQueryModel alipayTradeQueryModel) throws AlipayApiException {
        return alipayService.tradeQuery(alipayTradeQueryModel);
    }

    /**
     * 订单退款
     */
    @PostMapping("/tradeRefund")
    @ResponseBody
    public Result<?> tradeRefund(AlipayTradeRefundModel alipayTradeRefundModel) throws AlipayApiException {
        return alipayService.tradeRefund(alipayTradeRefundModel);

    }

    /**
     * 退款订单查询
     */
    @PostMapping("/tradeRefundQuery")
    @ResponseBody
    public Result<?> tradeRefundQuery(AlipayTradeRefundModel alipayTradeRefundModel) throws AlipayApiException {
        return alipayService.tradeRefundQuery(alipayTradeRefundModel);
    }

    /**
     * 订单关闭
     */
    @PostMapping("/tradeClose")
    @ResponseBody
    public Result<?> tradeClose(AlipayTradeCloseModel alipayTradeCloseModel) throws AlipayApiException {
        return alipayService.tradeClose(alipayTradeCloseModel);
    }

    /**
     * 账单下载
     */
    @PostMapping("/billDownload")
    @ResponseBody
    public Result<?> billDownload(AlipayDataDataserviceBillDownloadurlQueryModel alipayDataDataserviceBillDownloadurlQueryModel) throws AlipayApiException {
        return alipayService.billDownload(alipayDataDataserviceBillDownloadurlQueryModel);
    }

    /**
     * 异步支付成功回调通知
     */
    @PostMapping("/notifyUrl")
    @ResponseBody
    public Result<?> notifyUrl(HttpServletRequest request){
        return alipayService.tradeNotify(request);
    }

    /**
     * 同步支付成功回调通知
     */
    @GetMapping("/returnUrl")
    @ResponseBody
    public Result<?> returnUrl(HttpServletRequest request) {
        return alipayService.tradeReturn(request);
    }

}
