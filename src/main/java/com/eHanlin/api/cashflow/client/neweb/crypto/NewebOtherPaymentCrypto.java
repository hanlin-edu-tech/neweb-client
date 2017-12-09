package com.eHanlin.api.cashflow.client.neweb.crypto;

import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndQuery;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

/**
 * 藍新非信用卡交易相關加密與摘要工具
 */
public class NewebOtherPaymentCrypto {

    private String merchantNumber;

    private String code;

    public NewebOtherPaymentCrypto(String merchantNumber, String code) {
        this.merchantNumber = merchantNumber;
        this.code = code;
    }

    /**
     * 付款API hash 值
     * @param amount
     * @param orderNumber
     */
    public String hash(int amount, long orderNumber) {
        return DigestUtils.md5Hex(merchantNumber + code + amount + orderNumber);
    }

    /**
     * 查詢API hash 值
     * @param time
     * @return
     */
    public String query(String time) {
        return DigestUtils.md5Hex(CashSystemFrontEndQuery.Operation.queryorders.name() + code + time);
    }

    /**
     * 非信用卡銷帳結果 hash 值
     * @param ordernumber
     * @param serialnumber
     * @param writeoffnumber
     * @param timepaid
     * @param paymenttype
     * @param amount
     * @param tel
     */
    public String writeoff(Object ordernumber, Object serialnumber, Object writeoffnumber, Object timepaid, Object paymenttype, Object amount, Object tel) {
        String str = "merchantnumber=" + merchantNumber +
                "&ordernumber=" + ordernumber +
                "&serialnumber=" + serialnumber +
                "&writeoffnumber=" + writeoffnumber +
                "&timepaid=" + timepaid +
                "&paymenttype=" + paymenttype +
                "&amount=" + amount +
                "&tel=" + tel +
                code;

        return DigestUtils.md5Hex(str);
    }

    /**
     * 藍新超商付款交易結果判斷式
     * @param data
     */
    public boolean isWriteoffCredible(Map data) {
        String checksum = writeoff(
            data.get("ordernumber"),
            data.get("serialnumber"),
            data.get("writeoffnumber"),
            data.get("timepaid"),
            data.get("paymenttype"),
            data.get("amount"),
            data.get("tel")
        );

        return checksum.equalsIgnoreCase((String) data.get("hash"));
    }

}
