package com.eHanlin.api.cashflow.client.neweb.api;

import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndPayment.PaymentType;

/**
 * 藍新金流非信用卡付款訂單查詢 (超商取貨、ATM)
 */
public class CashSystemFrontEndQuery extends NewebAPI<CashSystemFrontEndQuery> {

    private final static String API_NAME = "Query";

    private final static String DEFAULT_RESPONSE_ENCODING = "UTF-8";

    public final static String TIME_FORMAT = "yyyyMMddHHmmss";

    public CashSystemFrontEndQuery() {
        super(API_NAME);
        setResponseEncoding(DEFAULT_RESPONSE_ENCODING);
    }

    /**
     * 訂單編號
     * @param orderNumber
     */
    public CashSystemFrontEndQuery setOrderNumber(Long orderNumber) {
        return setParam("ordernumber", orderNumber.toString());
    }

    /**
     * 設定付款類型
     * @param paymentType
     */
    public CashSystemFrontEndQuery setPaymentType(PaymentType paymentType) {
        return setParam("paymenttype", paymentType.name());
    }

    /**
     * 西元年、24小時制完全相同參數值的查詢僅在time的一分鐘內有效 (藍新開發文件寫的，你看得懂嗎？)
     * @param time
     */
    public CashSystemFrontEndQuery setTime(String time) {
        return setParam("time", time);
    }

    /**
     * 查詢動作
     * @param operation
     */
    public CashSystemFrontEndQuery setOperation(Operation operation) {
        return setParam("operation", operation.name());
    }

    /**
     * 回傳資料編碼
     * @param responseEncoding
     */
    public CashSystemFrontEndQuery setResponseEncoding(String responseEncoding) {
        return setParam("responseencoding", responseEncoding);
    }

    public enum Operation {

        regetorder,

        queryorders

    }

}

