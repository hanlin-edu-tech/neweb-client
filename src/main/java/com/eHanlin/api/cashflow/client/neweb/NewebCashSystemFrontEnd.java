package com.eHanlin.api.cashflow.client.neweb;

import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndPayment;
import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndPayment.PaymentType;
import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndQuery;
import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndQuery.Operation;
import com.eHanlin.api.cashflow.client.neweb.api.NewebAPI;
import com.eHanlin.api.cashflow.client.neweb.response.NewebPaymentResponse;
import com.eHanlin.api.cashflow.client.neweb.response.NewebQueryResponse;
import com.eHanlin.api.cashflow.client.neweb.util.NewebCrypto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 藍新非信用卡交易 API
 */
@SuppressWarnings("unused")
public class NewebCashSystemFrontEnd {

    private final String endpoint;

    private final String merchantNumber;

    private final String code;

    private final NewebCrypto crypto;

    private final HttpInvoker http;

    private static final Integer DEFAULT_DUE_DATE = 5;

    public NewebCashSystemFrontEnd(String endpoint, String merchantNumber, String code) {
        this.endpoint = endpoint;
        this.merchantNumber = merchantNumber;
        this.code = code;
        this.crypto = new NewebCrypto(merchantNumber, code);
        this.http = new HttpInvoker();
    }

    /**
     * 虛擬帳號付款
     * @param orderNumber
     * @param amount
     */
    public NewebPaymentResponse atm(Long orderNumber, Integer amount) {
        return payment(PaymentType.ATM, orderNumber, amount);
    }

    /**
     * 超商付款
     * @param orderNumber
     * @param amount
     */
    public NewebPaymentResponse mmk(Long orderNumber, Integer amount) {
        return payment(PaymentType.MMK, orderNumber, amount);
    }

    /**
     * 藍新金流請求建立付款訂單
     */
    public NewebPaymentResponse payment(PaymentType paymentType, Long orderNumber, Integer amount) {
        CashSystemFrontEndPayment api = new CashSystemFrontEndPayment()
            .setMerchantNumber(merchantNumber)
            .setPaymentType(paymentType)
            .setOrderNumber(orderNumber)
            .setAmount(amount)
            .setDuedate(DEFAULT_DUE_DATE)
            .setHash(crypto.otherPayment(amount, orderNumber));

        return new NewebPaymentResponse(call(api));
    }

    /**
     * 藍新金流訂單查詢
     * @param paymentType
     * @param orderNumber
     * @return
     */
    public NewebQueryResponse query(PaymentType paymentType, Long orderNumber) {
        String time = new SimpleDateFormat(CashSystemFrontEndQuery.TIME_FORMAT).format(new Date());
        CashSystemFrontEndQuery api = new CashSystemFrontEndQuery()
                .setMerchantNumber(merchantNumber)
                .setPaymentType(paymentType)
                .setOrderNumber(orderNumber)
                .setOperation(Operation.queryorders)
                .setTime(time)
                .setHash(crypto.query(time));

        return new NewebQueryResponse(call(api));
    }

    private String call(NewebAPI api) {
        return http.post(endpoint + api.name(), api.param());
    }

}

