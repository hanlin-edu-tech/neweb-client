package com.eHanlin.api.cashflow.client.neweb.response;

import java.util.HashMap;
import java.util.Map;

/**
 * 藍新金流 API 基本回應
 */
public class NewebResponse {

    private String responseBody;

    protected Map<String, String> responseBodyMap;

    public NewebResponse(String responseBody) {
        this.responseBody = responseBody;
        this.responseBodyMap = parseBodyStringToMap(responseBody);
    }

    public Map<String, String> getResponseMap() {
        return responseBodyMap;
    }

    public boolean isSuccess() {
        return rc().equals("0");
    }

    public String rc() {
        return responseBodyMap.get("rc");
    }

    public String getMerchantNumber() {
        return responseBodyMap.get("merchantnumber");
    }

    public Long getOrderNumber() {
        String ordernumber = responseBodyMap.get("ordernumber");
        return ordernumber == null ? null : Long.parseLong(ordernumber);
    }

    public Integer getAmount() {
        String amount = responseBodyMap.get("amount");
        return amount == null ? null : Integer.parseInt(amount);
    }

    public String getChecksum() {
        return responseBodyMap.get("checksum");
    }

    private Map<String, String> parseBodyStringToMap(String responseBody) {
        Map<String, String> result = new HashMap<>();
        String[] tokens = responseBody.split("&");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            int splitIndex = tokens[i].indexOf("=");
            if (splitIndex == -1) {
                result.put("token" + i, token);
            } else {
                result.put(token.substring(0, splitIndex), token.substring(splitIndex + 1));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return responseBody;
    }

}
