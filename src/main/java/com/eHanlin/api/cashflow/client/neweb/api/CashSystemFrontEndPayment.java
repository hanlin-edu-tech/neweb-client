package com.eHanlin.api.cashflow.client.neweb.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 藍新金流非信用卡付款 (超商取貨、ATM)
 */
public class CashSystemFrontEndPayment extends NewebAPI<CashSystemFrontEndPayment> {

    private final static String API_NAME = "Payment";

    public final static String DEFAULT_BANK_ID = "007";

    private final static String DUEDATE_FORMAT = "yyyyMMdd";

    public CashSystemFrontEndPayment() {
        super(API_NAME);
    }

    /**
     * 訂單編號
     * @param orderNumber
     */
    public CashSystemFrontEndPayment setOrderNumber(Long orderNumber) {
        return setParam("ordernumber", orderNumber.toString());
    }

    public CashSystemFrontEndPayment setAmount(Integer amount) {
        return setParam("amount", amount.toString());
    }

    /**
     * 設定付款類型
     * @param paymentType
     */
    public CashSystemFrontEndPayment setPaymentType(PaymentType paymentType) {
        if (paymentType == PaymentType.ATM) {
            setParam("bankid", DEFAULT_BANK_ID);
        }

        return setParam("paymenttype", paymentType.name());
    }

    /**
     * 設定到期日，到指定傳入日期當天結束為止
     * @param dueDate
     */
    public CashSystemFrontEndPayment setDuedate(Date dueDate) {
        return setParam("duedate", new SimpleDateFormat(DUEDATE_FORMAT).format(dueDate));
    }

    /**
     * 設定到期日天數，若不設定則預設三天
     */
    public CashSystemFrontEndPayment setDuedate(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return setDuedate(calendar.getTime());
    }

    public enum PaymentType {

        /**
         * 虛擬帳號
         */
        ATM,

        /**
         * 超商付款
         */
        MMK

    }

}

