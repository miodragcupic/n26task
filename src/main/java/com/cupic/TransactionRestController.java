package com.cupic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Miodrag on 9/24/2016.
 */

@RestController
public class TransactionRestController {

    private Map<Long, Transaction> transactionMap = new HashMap<Long, Transaction>();


    /**
     *
     * @return all transaction in a form of a Map
     */
    @GetMapping("/transactions")
    public Map getTransactions() {
        return transactionMap;
    }

    /**
     *
     * @param transaction_id transaction id
     * @return transaction with the corresponding transaction_id
     */
    @GetMapping("/transactionservice/transaction/{transaction_id}")
    public ResponseEntity getTransaction(@PathVariable("transaction_id") Long transaction_id) {

        Transaction transaction = transactionMap.get(transaction_id);
        if (transaction == null) {
            return new ResponseEntity("No Transaction found for ID " + transaction_id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(transaction, HttpStatus.OK);
    }

    /**
     *
     * @param transactionType
     * @return list of transactions with the corresponding types
     */
    @GetMapping("/transactionservice/types/{type}")
    public ResponseEntity getType(@PathVariable("type") String transactionType) {
        String returnResponse = TransactionUtility.getTypeIds(transactionMap, transactionType);
        if (returnResponse == null) {
            return new ResponseEntity("No Transaction found for ID " + transactionType, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(returnResponse, HttpStatus.OK);
    }

    /**
     *
     * @param transactionId
     * @return sum of transactions that have the same parent_id for a
     * specific transaction_id
     */
    @GetMapping("/transactionservice/sum/{transaction_Id}")
    public ResponseEntity getSum(@PathVariable("transaction_Id") Long transactionId) {
        String returnResponse = TransactionUtility.calculateSum(transactionMap, transactionId);
        if (returnResponse == null) {
            return new ResponseEntity("No Transaction found for ID " + transactionId, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(returnResponse, HttpStatus.OK);
    }

    /**
     *
     * @param transaction_id
     * @param inputString
     * @return Response of the server for creating a transaction
     * @throws IOException
     */
    @PutMapping("/transactionservice/transaction/{transaction_id}")
    public ResponseEntity addTransaction(@PathVariable Long transaction_id, @RequestBody String inputString) throws IOException {
        Transaction transaction = TransactionUtility.readTransaction(inputString);
        transactionMap.put(transaction_id, transaction);
        return new ResponseEntity(transaction, HttpStatus.MULTI_STATUS.OK);
    }
}
