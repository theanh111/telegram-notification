package com.printway.service;

import com.printway.entity.db.TblProductPostStore;
import com.printway.repository.TblProductPostStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TblProductPostStoreService implements BasicService<TblProductPostStore> {
    @Autowired
    private TblProductPostStoreRepository tblProductPostStoreRepository;

    @Override
    public TblProductPostStore findById(Integer id) {
        return tblProductPostStoreRepository.findOne(id);
    }

    public TblProductPostStore findBySku(String sku) {
        return tblProductPostStoreRepository.findBySku(sku);
    }

    @Override
    public TblProductPostStore update(TblProductPostStore tblProductPostStore) {
        return tblProductPostStoreRepository.update(tblProductPostStore);
    }

    @Override
    public TblProductPostStore create(TblProductPostStore tblProductPostStore) {
        return tblProductPostStoreRepository.insert(tblProductPostStore);
    }

    public List<TblProductPostStore> updateList(List<TblProductPostStore> tblProductPostStoreList) {
        return tblProductPostStoreRepository.update(tblProductPostStoreList);
    }

}
