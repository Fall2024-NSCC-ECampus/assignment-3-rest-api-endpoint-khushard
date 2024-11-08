package com.example.restapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    private Product product;

    private String orderStatus;
    private double totalAmount;
    private String deliveryAddress;

}
