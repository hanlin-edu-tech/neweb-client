package com.eHanlin.api.cashflow.client.neweb.util;

import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndQuery;
import org.apache.commons.codec.digest.DigestUtils;

public class NewebCrypto {

    private String merchantNumber;

    private String code;

    public NewebCrypto(String merchantNumber, String code) {
        this.merchantNumber = merchantNumber;
        this.code = code;
    }

    /**
     * 信用卡 hash 值
     * @param orderNumber
     * @param amount
     * @return
     */
    public String creditCardPayment(long orderNumber, int amount) {
        return DigestUtils.md5Hex(merchantNumber + orderNumber + code + amount + ".00");
    }

    /**
     * 非信用卡 hash 值
     * @param amount
     * @param orderNumber
     */
    public String otherPayment(int amount, long orderNumber) {
        return DigestUtils.md5Hex(merchantNumber + code + amount + orderNumber);
    }

    /**
     * 信用卡交易結果 hash 值
     * @param order
     * @param prc
     * @param src
     * @param amount
     * @return
     */
    public String feedback(String order, String prc, String src, String amount) {
        return DigestUtils.md5Hex(merchantNumber + order + prc + src + code + amount);
    }

    /**
     * 信用卡交易結果顯示 hash 值
     * @param order
     * @param finalResult
     * @param prc
     * @param src
     * @param amount
     * @return
     */
    public String mppFinalResult(String order, String finalResult, String prc, String src, String amount) {
        return DigestUtils.md5Hex(merchantNumber + order + finalResult + prc + code + src + amount);
    }

    /**
     * 非信用卡銷帳結果 hash 值
     * @param order
     * @param serial
     * @param writeoff
     * @param timepaid
     * @param paymenttype
     * @param amount
     * @param tel
     */
    public String writeoff(Object order, Object serial, Object writeoff,
                                  Object timepaid, Object paymenttype, Object amount, Object tel)
    {
        String str = "merchantnumber=" + merchantNumber +
                "&ordernumber=" + order +
                "&serialnumber=" + serial +
                "&writeoffnumber=" + writeoff +
                "&timepaid=" + timepaid +
                "&paymenttype=" + paymenttype +
                "&amount=" + amount +
                "&tel=" + tel +
                code;

        return DigestUtils.md5Hex(str);
    }

    /**
     * 非信用卡查詢 hash 值
     * @param time
     * @return
     */
    public String query(String time) {
        return DigestUtils.md5Hex(CashSystemFrontEndQuery.Operation.queryorders.name() + code + time);
    }

}
