package com.printway.repository;

import com.printway.entity.db.TblProductPostStore;
import org.springframework.stereotype.Repository;

@Repository
public class TblProductPostStoreRepository extends BaseRepository<TblProductPostStore, Integer> {
    public TblProductPostStoreRepository(){
        super(TblProductPostStore.class);
    }

}
