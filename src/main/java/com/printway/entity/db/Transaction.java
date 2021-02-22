package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbltransaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "payment_config_id")
    private int paymentConfigId;
    @Column(name = "order_return_id")
    private String orderReturnedId;
    @Column(name = "total_amount")
    private double amount;
    @Column(name = "tran_status")
    private String status;
    @Column(name = "dtime_entered")
    private String createTime;

}
