package com.eHanlin.api.cashflow.client.neweb.crypto;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

/**
 * 藍新信用卡交易加密與摘要工具
 */
public class NewebCreditCardPaymentCrypto {

    private String merchantNumber;

    private String code;

    public NewebCreditCardPaymentCrypto(String merchantNumber, String code) {
        this.merchantNumber = merchantNumber;
        this.code = code;
    }

    /**
     * 信用卡 hash 值
     * @param orderNumber
     * @param amount
     * @return
     */
    public String checksum(long orderNumber, int amount) {
        return DigestUtils.md5Hex(merchantNumber + orderNumber + code + amount + ".00");
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
    public String finalResult(String order, String finalResult, String prc, String src, String amount) {
        return DigestUtils.md5Hex(merchantNumber + order + finalResult + prc + code + src + amount);
    }

    /**
     * 藍新信用卡交易，第一階段交易結果(feedback)判斷式
     * @param data
     * @param salt
     * @return
     */
    public boolean isFeedbackCredible(Map<String, String> data, String salt) {
        String feedbackOrderNumber = data.get("OrderNumber");
        String feedbackPRC = data.get("PRC");
        String feedbackSRC = data.get("SRC");
        String feedbackAmount = data.get("Amount");
        String feedbackCheckSum = data.get("CheckSum");
        String feedbackBankResponseCode = data.get("BankResponseCode");
        String feedbackApprovalCode = data.get("ApprovalCode");
        String checksum = feedback(feedbackOrderNumber, feedbackPRC, feedbackSRC, feedbackAmount);

        return checksum.equalsIgnoreCase(feedbackCheckSum) &&
                "0".equals(feedbackPRC) &&
                "0".equals(feedbackSRC) &&
                "0/00".equals(feedbackBankResponseCode) &&
                feedbackApprovalCode.length() <= 6;
    }

    /**
     * 藍新信用卡交易，第二階段交易結果(result)判斷式
     * @param data
     * @param feedback
     * @return
     */
    public boolean isFinalResultSuccess(Map<String, String> data, Map feedback) {
        try {
            String finalResult = data.get("final_result");
            String finalReturnPRC = data.get("final_return_PRC");
            String finalReturnSRC = data.get("final_return_SRC");
            String finalReturnApproveCode = data.get("final_return_ApproveCode");
            String finalReturnBankRC = data.get("final_return_BankRC");
            String checksum = finalResult(
                data.get("P_OrderNumber"),
                data.get("final_result"),
                data.get("final_return_PRC"),
                data.get("final_return_SRC"),
                data.get("P_Amount")
            );

            boolean isFinalResultCredible = checksum.equalsIgnoreCase(data.get("P_CheckSum"));

            return isFinalResultCredible &&
                    finalResult.equals("1") &&
                    finalReturnPRC.equals("0") &&
                    finalReturnPRC.equals(feedback.get("PRC")) &&
                    finalReturnSRC.equals("0") &&
                    finalReturnSRC.equals(feedback.get("SRC")) &&
                    finalReturnApproveCode.length() <= 6 &&
                    finalReturnApproveCode.equals(feedback.get("ApprovalCode")) &&
                    finalReturnBankRC.equals("0/00") &&
                    finalReturnBankRC.equals(feedback.get("BankResponseCode"));

        } catch (NullPointerException e) {
            // If any field loss, determine that transaction fail.
            return false;
        }
    }

}
