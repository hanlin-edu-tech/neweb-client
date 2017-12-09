package com.eHanlin.api.cashflow.client.neweb.api;

import java.util.HashMap;
import java.util.Map;

public abstract class NewebAPI<T> {

    private String name;

    Map<String, String> params;

    NewebAPI(String name) {
        this.name = name;
        params = new HashMap<>();
        setReturnValue(true);
    }

    /**
     * 取得 API 名稱
     */
    public String name() {
        return name;
    }

    public T setParam(String name, String value) {
        if (value == null || value.equals("")) {
            params.remove(name);
        } else {
            params.put(name, value);
        }

        return (T) this;
    }

    public T setReturnValue(boolean returnValue) {
        return setParam("returnvalue", returnValue ? "1" : "0");
    }

    public T setMerchantNumber(String merchantNumber) {
        return setParam("merchantnumber", merchantNumber);
    }

    public T setHash(String hash) {
        return setParam("hash", hash);
    }

    /**
     * 請求參數
     */
    public Map<String, String> param() {
        return params;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", name(), param());
    }

}

