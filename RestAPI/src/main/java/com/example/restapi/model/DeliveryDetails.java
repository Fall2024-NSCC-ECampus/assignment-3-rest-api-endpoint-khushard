package com.example.restapi.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "delivery_details")
@Data
public class DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Order order;

    private String orderName;
    private String address;
    private String status;
    private String trackingNumber;
}
