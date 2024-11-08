package com.example.restapi.controller;

import com.example.restapi.model.DeliveryDetails;
import com.example.restapi.repository.DeliveryDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



/**
 * REST Controller for managing all Delivery operations.
 * Provides API endpoints for CRUD operations.
 * */
@RestController
@RequestMapping("/api/delivery-details")
public class DeliveryController {

    @Autowired
    private DeliveryDetailsRepo deliveryRepo;

    /**
     * Gets all Delivery Details from the database.
     * @return ResponseEntity that contains either:
     *      -204 No Content if no Deliveries are in the system
     *      -200 OK with a list of all Deliveries in the system after determining if Deliveries exist.
     * */
    @GetMapping
    public ResponseEntity<List<DeliveryDetails>> getAllDeliveryDetails() {
        List<DeliveryDetails> deliveryDetails = deliveryRepo.findAll();
        if (deliveryDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deliveryDetails);
    }

    /**
     * Gets a deliver based on the ID of the delivery.
     * @param id The ID of the delivery to be retrieved.
     * @return ResponseEntity containing either:
     *      - 200 OK with the found delivery
     *      - 404 Not Found if the delivery doesn't exist in database.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDetails> getDeliveryDetailsById(@PathVariable long id) {
        return deliveryRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new delivery in the database.
     * @param deliveryDetails The delivery object to be created.
     * @return ResponseEntity containing:
     *      - 201 Created with the delivery that was created.
     * */
    @PostMapping
    public ResponseEntity<DeliveryDetails> createDeliveryDetails(@RequestBody DeliveryDetails deliveryDetails) {
        DeliveryDetails newDeliveryDetails = deliveryRepo.save(deliveryDetails);
        return ResponseEntity.status(201).body(newDeliveryDetails);
    }

    /**
     * Updates an existing delivery in the database.
     * @param id The ID of the delivery to update
     * @param deliveryDetails The delivery that is to be updated.
     * @return ResponseEntity containing either:
     *      - 200 OK with the updated delivery.
     *      - 404 Not Found if the delivery doesn't exist.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDetails> updateDeliveryDetails(@PathVariable long id, @RequestBody DeliveryDetails deliveryDetails) {
        if (!deliveryRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        deliveryDetails.setId(id);
        return ResponseEntity.ok(deliveryRepo.save(deliveryDetails));
    }

    /**
     * Deletes a delivery from the database
     * @param id The ID of the delivery to be deleted.
     * @return ResponseEntity containing either:
     *      - 204 No Content if deleted successfully
     *      - 404 Not Found if delivery doesn't exist
     * */
    @DeleteMapping("{id}")
    public ResponseEntity<DeliveryDetails> deleteDeliveryDetails(@PathVariable long id) {
        if (!deliveryRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        deliveryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
