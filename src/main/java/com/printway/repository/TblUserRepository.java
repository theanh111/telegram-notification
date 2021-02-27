package com.printway.repository;

import com.printway.entity.db.TblUserEntity;

public class TblUserRepository extends BaseRepository<TblUserEntity, Integer>{
    public TblUserRepository(){
        super(TblUserEntity.class);
    }
}
