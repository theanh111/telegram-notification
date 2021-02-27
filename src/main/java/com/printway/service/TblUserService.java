package com.printway.service;

import com.printway.entity.db.TblUserEntity;
import com.printway.repository.TblUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TblUserService implements BasicService<TblUserEntity>{
    @Autowired
    private TblUserRepository tblUserRepository;

    @Override
    public TblUserEntity findById(Integer id) {
        return tblUserRepository.findOne(id);
    }

    @Override
    public TblUserEntity update(TblUserEntity tblUserEntity) {
        return tblUserRepository.update(tblUserEntity);
    }

    @Override
    public TblUserEntity create(TblUserEntity tblUserEntity) {
        return tblUserRepository.insert(tblUserEntity);
    }
}
