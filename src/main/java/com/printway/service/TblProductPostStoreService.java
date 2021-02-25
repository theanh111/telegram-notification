package com.printway.service;

import com.printway.entity.db.TblProductPostStore;
import com.printway.repository.TblProductPostStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TblProductPostStoreService {
    @Autowired
    private TblProductPostStoreRepository tblProductPostStoreRepository;

    public TblProductPostStore findById(Integer id){
        return tblProductPostStoreRepository.findOne(id);
    }
}
