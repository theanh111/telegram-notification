package com.printway.repository;

import com.printway.entity.db.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository extends BaseRepository<Order, Integer> {
    public OrderRepository() {
        super(Order.class);
    }

    @Override
    public Order insert(Order entity) {
        return super.insert(entity);
    }

    @Override
    public Order update(Order entity) {
        return super.update(entity);
    }

    @Override
    public Order findOne(Integer integer) {
        return super.findOne(integer);
    }

    public List<Order> findByTranId(String tranId) {
        String query = "select * from tblorder where tran_id = :tranId";
        Map<String, Object> params = new HashMap<>();
        params.put("tranId", tranId);
        return query(query, true, params);
    }

    public List<Order> findByStatus(String status) {
        String query = "select * from tblorder where order_status = :status";
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        return query(query, true, params);
    }
}
