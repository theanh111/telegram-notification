package com.printway.repository;

import com.printway.entity.db.TblUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TblUserRepository extends BaseRepository<TblUserEntity, Integer>{
    public TblUserRepository(){
        super(TblUserEntity.class);
    }
}
