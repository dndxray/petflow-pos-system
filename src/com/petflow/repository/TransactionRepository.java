package com.petflow.repository;

import java.util.Optional;

import com.petflow.model.Transaction;

public class TransactionRepository extends Repository<Transaction> {

    @Override
    public Optional<Transaction> findById(String id) {
        for (Transaction t : data) {
            if (t.getTransactionId().equals(id)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Transaction t : data) {
            total += t.getTotalAmount();
        }
        return total;
    }
}