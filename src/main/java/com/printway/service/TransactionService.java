package com.printway.service;

import com.printway.entity.db.Transaction;
import com.printway.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findById(String id) {
        return transactionRepository.findOne(Integer.parseInt(id));
    }
    public Transaction create(Transaction transaction){
        return transactionRepository.insert(transaction);
    }
    public Transaction updateTransaction(Transaction transaction){
        return transactionRepository.update(transaction);
    }

}
