package com.printway.repository;

import com.printway.entity.db.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TransactionRepository extends BaseRepository<Transaction, Integer> {
    public TransactionRepository() {
        super(Transaction.class);
    }

    @Override
    public Transaction insert(Transaction entity) {
        return super.insert(entity);
    }

    @Override
    public Transaction findOne(Integer integer) {
        return super.findOne(integer);
    }

}
