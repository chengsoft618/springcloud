package com.iyysoft.msdp.dp.app.utils.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.iyysoft.msdp.dp.app.entity.PayProductInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
public class AliPayInfo {

    private static String appId = "******";
    private static String sellerId = "*****";
    private static String privateKey = "****/J86qbpVq+kLBB8QKUpZukGb9f2AUA3QUmkFAYuMQhJNdJM8pXhXidLLsy6ZBD0tfHwi3Yu3lDLf3Osv8qPYVRD1UGy6HqL4uXRb/Vtb7jO0immcM5LacLIh4jB5mmJudMKkhJvdpzNL0XbG2aLcTmncwGIigGwP0Wm6lU8qLvMcHyhfPpxvi83v+vRA5jkNcR36Cc37T8Op5enFzEhxF+P+7jlhv1k0Rqh1jyeVSs80j7dkm4s5v/TDVBSN9rZfcn9MUGQg+IguTZr7aeI8ScM0OU9JnUbZI0Itkhle531ET6pUH88GiUaUARqhGvW5oUzAgMBAAECggEAG5tZNau61KUg934DOWIViIm0mhFb3AGC3qyDgGe8UvbmqtJKHHbAypE0vM6MhxlSRRHVDiaUBOywNYdNCcluBX7MOo9fbu6XYKoY1j2mAU6vRVdxuqc/8D9xhMWkfQDobz5s9BAHEPRTsEGQlY7JmjYc+B8AqZn0h2Y04WQl3uT5DrghZwCWbde87RnO99c/gmcS8DYVfCC8Q//Hs5TNQp3GhQa/1PZ/uVOfQnKidnvN4llBTkhpJ+DQK7kF0N5nVph8CRY83BFBkZTw7654d2+Q+n5sDFwVX17jJ1+QnE4S/qRDhnzxWBX/rBtzP0ZRQu/TIQ5Vu1vSr38BlprAYQKBgQD3RYg8HFABXoJQcdClW8nv6Ke3X5I9fR0ii7Ei2ZFMoNUunY0+165I2qRx3/1vI1L6mWkJMvKgFaplQhMkshkxlZVHrf5RLb/BbykgjgjjnlEV8Oo8w2G3I8MvNzMKdHvXfETdPZgrRANEPnORaIoZczI9S3KBctb5QU+x01SlPQKBgQDLsKgEoK9PdjksaRyRU9wg4va96bjeoEt5hZJGSfz4W3/XrQwEV8IOxpCSxQJgTYdTyL6lQjCsM+zQcQwzB8QR7iHS03lGd3pjRN3ilk1dk4trXwhChGxFncn33dwtabZIwB9VbG2gGOZ3yhOJTK3hldnw2clptB+uiM+WjxXbLwKBgDL7GwOJstUVbzi2vpXZPuE/zpR4F+aljQQ6WOMeiF6MKmfaA+0Y+s36hj0UOWRYnBse1hEXd/6ZePA3l0rRjXAzrvuAXBbQn+B50rjrj9meIomrih/NCLCBpxRtBRD6wy3CaVBUCrOg6it/1MP+Ebr9jTYmAA3AZ6IsXQoE0EbhAoGAJHqRzKpeWzPID30PobNMFBOxrtdhxXUdrzjNtorfpDb20VBre4ef+Hd1QuPHhim8V7P2+K+++cge3EGytpzob3GfWxuGd6lEIO0fc8oJNv4KlQMi/o6quVkF578zBGsPOz41Hl5Zz7dF5yQolYlHBsSIkIrgeG7K0b4st0EO3/8CgYAOf70rGDCnNiiy6GePBI5LlTxayqY6BM6Jdv/6i1I/xqf85kWY4qvVmam+vqXQQs42pVEcoOcWsbY7NA8I3KJHisf5/FAzriLrx1jIWOAPjsB15D7wH88ihm1d+ncardHQmL4unYPkCKqAnErTj7m1hdyxttwrXam8QsT1cRtF4Q==";
    private static String publicKey = "*****/Ppw73b32FokmqgxdmUbun0sP3p53WymA6sQSawqa6ro/5dKBNS7Vnz8rzdu8OPaHO0ycAenCnkZ+RVJzHg5epNVhvNRlhWMo60VnflDqb8gd0gOhylGovhL7k6nTtfyxTNW4Xknl9yXyt1skewTa3mlYJCXxx+ADY+RusGEn7ZXY6Y4QIxi39sL2cK8+OSDZRAf0bjB5iqS0B2qPH5pwOf/7SFfcNldmYfLjuygOn5Rk/xt28kCKTR4+TRjQTumxLn3Ahly22GZCyyE/sk4+cpMghK7WfCwTnz7CSenFgxYg48TNR0pWJLbQIDAQAB";
    private static String unitName = "微悦科技";
    private boolean rsa2 = true;

    /**
     * 退款
     *
     * @param refundInfo
     * @return
     * @throws AlipayApiException
     */
    public static boolean doRefund(AliRefundInfo refundInfo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "GBK", publicKey, "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        String content = "{}";
        try {
            content = AliJSONUtil.toUnderlineJSONString(refundInfo);
        } catch (JsonProcessingException ex) {
        }
        request.setBizContent(content);
        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                //System.out.println("调用成功");
                return true;
            } else {
                //System.out.println("调用失败");
                return false;
            }
        } catch (AlipayApiException ex) {
            throw ex;
        }
    }

    /**
     * 转账
     *
     * @param transferInfo
     * @return
     * @throws AlipayApiException
     */
    public static boolean doTransfer(AliTransferInfo transferInfo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "GBK", publicKey, "RSA2");
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        String content = "{}";
        try {
            content = AliJSONUtil.toUnderlineJSONString(transferInfo);
        } catch (JsonProcessingException ex) {
        }
        request.setBizContent(content);
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return true;
            } else {
                System.out.println("调用失败");
                return false;
            }
        } catch (AlipayApiException ex) {
            throw ex;
        }
    }

    /**
     * 交易查詢
     *
     * @param aliTransferQueryInfo
     * @return
     * @throws AlipayApiException
     */
    public static AlipayTradeQueryResponse doOrderQuery(AliTransferQueryInfo aliTransferQueryInfo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "GBK", publicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        String content = "{}";
        try {
            content = AliJSONUtil.toUnderlineJSONString(aliTransferQueryInfo);
        } catch (JsonProcessingException ex) {
        }
        request.setBizContent(content);
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return response;
            } else {
                System.out.println("调用失败");
                return null;
            }
        } catch (AlipayApiException ex) {
            throw ex;
        }
    }

    /**
     * 退款查詢
     *
     * @param refundQueryInfo
     * @return
     * @throws AlipayApiException
     */
    public static AlipayTradeFastpayRefundQueryResponse doOrderRefundQuery(AliRefundQueryInfo refundQueryInfo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "GBK", publicKey, "RSA2");
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        String content = "{}";
        try {
            content = AliJSONUtil.toUnderlineJSONString(refundQueryInfo);
        } catch (JsonProcessingException ex) {
        }
        request.setBizContent(content);
        try {
            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return response;
            } else {
                System.out.println("调用失败");
                return null;
            }
        } catch (AlipayApiException ex) {
            throw ex;
        }
    }

    public String getAliPayInfo(PayProductInfo info) {

        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(this.appId, rsa2);
        params.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + DecimalUtil.getAmountStr(info.getTotalAmnt()) + "\",\"subject\":\"" + this.unitName + "\",\"body\":\"" + info.getProductName() + "\",\"out_trade_no\":\"" + info.getTradeNo() + "\"}");
        params.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.put("notify_url", info.getNotifyUrl());

        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        String orderString = orderParam + "&" + sign;

        return orderString;
    }
}
