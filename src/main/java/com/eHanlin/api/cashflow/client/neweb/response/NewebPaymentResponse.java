package com.eHanlin.api.cashflow.client.neweb.response;

public class NewebPaymentResponse extends NewebResponse {

    public NewebPaymentResponse(String responseBody) {
        super(responseBody);
    }

    /**
     * 取得轉入銀行代碼
     */
    public String getBankId() {
        return responseBodyMap.get("bankid");
    }

    /**
     * 取得虛擬繳費帳號
     */
    public String getVirtualAccount() {
        return responseBodyMap.get("virtualaccount");
    }

    /**
     * 取得超商繳費代碼
     */
    public String getPayCode() {
        return responseBodyMap.get("paycode");
    }

}

