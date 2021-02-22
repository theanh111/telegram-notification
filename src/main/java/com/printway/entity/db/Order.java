package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblorder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "tran_id")
    private String transactionId;
    @Column(name = "currency")
    private String currency;
    @Column(name = "order_amount")
    private double amount;
    @Column(name = "order_status")
    private String status;
}
