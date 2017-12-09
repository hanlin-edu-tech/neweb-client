package com.eHanlin.api.cashflow.client.neweb.response;

import com.eHanlin.api.cashflow.client.neweb.api.CashSystemFrontEndPayment.PaymentType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 藍新回傳查詢結果
 */
public class NewebQueryResponse extends NewebResponse {

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public NewebQueryResponse(String responseBody) {
        super(responseBody);
    }

    public String rc2() {
        return responseBodyMap.get("rc2");
    }

    public String time() {
        return responseBodyMap.get("time");
    }

    public String getSerialNumber() {
        return responseBodyMap.get("serialnumber");
    }

    public String getWriteoffNumber() {
        return responseBodyMap.get("writeoffnumber");
    }

    public PaymentType getPaymentType() {
        String paymentType = responseBodyMap.get("paymenttype");
        return PaymentType.valueOf(paymentType);
    }

    public Integer getStatus() {
        String status = responseBodyMap.get("status");
        return status == null ? null : Integer.parseInt(status);
    }

    public Date getTimeCreated() {
        String timeCreated = responseBodyMap.get("timecreated");
        return parseDate(timeCreated);
    }

    public Date getTimePaid() {
        String timePaid = responseBodyMap.get("timepaid");
        return parseDate(timePaid);
    }

    public Integer getPayCount() {
        String payCount = responseBodyMap.get("paycount");
        return payCount == null ? null : Integer.parseInt(payCount);
    }

    public Integer getPaidAmount() {
        String paidAmount = responseBodyMap.get("paidamount");
        return paidAmount == null ? null : Integer.parseInt(paidAmount);
    }

    private static Date parseDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        try {
            return new SimpleDateFormat(TIME_FORMAT).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

}

