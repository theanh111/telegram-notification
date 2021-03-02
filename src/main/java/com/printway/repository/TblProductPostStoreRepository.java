package com.printway.repository;

import com.printway.entity.db.PaymentConfig;
import com.printway.entity.db.TblProductPostStore;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TblProductPostStoreRepository extends BaseRepository<TblProductPostStore, Integer> {
    public TblProductPostStoreRepository(){
        super(TblProductPostStore.class);
    }

    public TblProductPostStore findBySku(String sku) {
        String query = "select * from tblproduct_post_store where sku = :sku";
        Map<String, Object> params = new HashMap<>();
        params.put("sku", sku);
        return queryOne(query, true, params);
    }
}
