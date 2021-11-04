package cn.xiaostudy.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description 支付宝支付配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

    /**
     * 应用APPID，收款账号既是您的APPID对应支付宝账号
     */
    private String appId;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String merchantPrivateKey;

    /**
     * 对应APPID下的支付宝公钥
     */
    private String alipayPublicKey;

    /**
     * 服务器异步通知页面路径
     */
    private String notifyUrl;

    /**
     * 页面跳转同步通知页面路径
     */
    private String returnUrl;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 字符编码格式
     */
    private String charset;

    /**
     * 格式
     */
    private String format;

    /**
     * 支付宝网关: https://openapi.alipay.com/gateway.do
     *
     * 沙箱网关: https://openapi.alipaydev.com/gateway.do
     */
    private String gatewayUrl;

    /**
     * 支付宝Client
     */
    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(this.getGatewayUrl(), this.getAppId(), this.getMerchantPrivateKey(), this.getFormat(), this.getCharset(), this.getAlipayPublicKey(), this.getSignType());
    }

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            String logPath = ResourceUtils.getURL("classpath:").getPath();
            writer = new FileWriter(logPath + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


