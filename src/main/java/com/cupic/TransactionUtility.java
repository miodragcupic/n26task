package com.cupic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Miodrag on 9/24/2016.
 * This class is intended to serve as a Utility class to perform the necessary
 * calculations, conversions, inputs and outputs.
 */
public class TransactionUtility {

    /**
     * Returns transaction ids for a specific transaction type.
     *
     * @param transactions map of transactions
     * @param type type of transaciton
     * @return params that represents a String of types for a specific transaction_id
     */
    public static String getTypeIds(Map <Long,Transaction> transactions, String type) {
        StringBuilder params = new StringBuilder();
        params.append("[");
        for(Map.Entry<Long, Transaction> entry : transactions.entrySet()) {
            Transaction transaction = entry.getValue();
            Long transactionId = entry.getKey();
            if(transaction.getType().equals(type)) {
                params.append(transactionId);
                params.append(", ");
            }
        }
        // necessary formatting in order to evade adding ", " at the end
        params = new StringBuilder(params.toString()
                .substring(0, params.toString().length() - 2));
        params.append("]");
        return params.toString();
    }

    /**
     * Calculates a sum of transactions with same parent_ids
     *
     * @param transactions map of transactions
     * @param transactionId transaction id
     * @return sum of transactions amounts
     */
    public static String calculateSum(Map<Long, Transaction> transactions, Long transactionId) {
        double sum = 0;
        StringBuilder returnSum = new StringBuilder();
        returnSum.append("{\"sum\":");
        Long parentId = transactions.get(transactionId).getParentId();
        for(Transaction transaction : transactions.values()) {
            if(parentId.equals(transaction.getParentId())) {
                sum += transaction.getAmount();
            }
        }
        returnSum.append(sum);
        returnSum.append("}");
        return returnSum.toString();
    }

    public static Transaction readTransaction(String inputString) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(inputString);
        double amount = root.get("amount").asDouble();
        String type = root.get("type").asText();
        Long parentId = root.get("parent_id").asLong();
        Transaction transaction = new Transaction(type, amount, parentId);
        return transaction;
    }
}
