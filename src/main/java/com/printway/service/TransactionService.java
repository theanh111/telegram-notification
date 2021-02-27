package com.printway.service;

import com.printway.entity.db.Transaction;
import com.printway.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TransactionService implements BasicService<Transaction>{
    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public Transaction findById(Integer id) {
        return transactionRepository.findOne(id);
    }

    @Override
    public Transaction update(Transaction transaction) {
        return transactionRepository.update(transaction);
    }

    public Transaction create(Transaction transaction){
        return transactionRepository.insert(transaction);
    }
    public Transaction updateTransaction(Transaction transaction){
        return transactionRepository.update(transaction);
    }

}
