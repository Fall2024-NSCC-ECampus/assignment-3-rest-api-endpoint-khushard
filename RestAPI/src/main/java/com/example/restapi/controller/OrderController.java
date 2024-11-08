package com.example.restapi.controller;

import com.example.restapi.model.Order;
import com.example.restapi.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST Controller for managing all Order operations.
 * Provides API endpoints for CRUD operations.
 * */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;



    /**
     * Gets all orders from the database.
     * @return ResponseEntity that contains either:
     *      -204 No Content if no orders are in the system
     *      -200 OK with a list of all orders in the system after determining if orders exist.
     * */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    /**
     * Gets an order based on the ID of the Order.
     * @param id The ID of the order to be retrieved.
     * @return ResponseEntity containing either:
     *      - 200 OK with the found order
     *      - 404 Not Found if the order doesn't exist in database.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) {
        return orderRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new order in the database.
     * @param order The order object to be created.
     * @return ResponseEntity containing:
     *      - 201 Created with the order that was created.
     * */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderRepo.save(order);
        return ResponseEntity.status(201).body(newOrder);
    }


    /**
     * Updates an existing order in the database.
     * @param id The ID of the order to update
     * @param order The order that is to be updated.
     * @return ResponseEntity containing either:
     *      - 200 OK with the updated order.
     *      - 404 Not Found if the order doesn't exist.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable long id, @RequestBody Order order) {
        if (!orderRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        order.setId(id);
        return ResponseEntity.ok(orderRepo.save(order));
    }


    /**
     * Deletes an order from the database
     * @param id The ID of the order to be deleted.
     * @return ResponseEntity containing either:
     *      - 204 No Content if deleted successfully
     *      - 404 Not Found if order doesn't exist
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        if (!orderRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
