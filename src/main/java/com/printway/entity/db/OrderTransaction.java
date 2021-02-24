package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "tblorder_transaction")
public class OrderTransaction {
    //    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "order_id")
    private String oderId;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "order_amount")
    private String orderAmount;
    @Column(name = "order_status")
    private String orderStatus;

}
