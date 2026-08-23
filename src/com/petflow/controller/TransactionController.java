package com.petflow.controller;
import com.petflow.database.TransactionDB;
import com.petflow.model.Transaction;

public class TransactionController {
    private TransactionDB transactionDB = new TransactionDB();
    public void checkout(Transaction transaction) {
        transactionDB.saveTransaction(transaction);
    }
    
    public double getTotalOmset() {
        try {
            return transactionDB.getTotalOmset();
        } 
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}