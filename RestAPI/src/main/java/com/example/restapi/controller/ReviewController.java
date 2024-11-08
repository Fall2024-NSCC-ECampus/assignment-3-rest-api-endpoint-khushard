package com.example.restapi.controller;

import com.example.restapi.model.Review;
import com.example.restapi.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST Controller for managing all Review operations.
 * Provides API endpoints for CRUD operations.
 * */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepo reviewRepo;

    /**
     * Gets all reviews from the database.
     * @return ResponseEntity that contains either:
     *      -204 No Content if no reviews are in the system
     *      -200 OK with a list of all reviews in the system after determining if reviews exist.
     * */
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviews);
    }

    /**
     * Gets a review based on the ID of the Review.
     * @param id The ID of the review to be retrieved.
     * @return ResponseEntity containing either:
     *      - 200 OK with the found review
     *      - 404 Not Found if the review doesn't exist in database.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable long id) {
        return reviewRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new review in the database.
     * @param review The order object to be created.
     * @return ResponseEntity containing:
     *      - 201 Created with the review that was created.
     * */
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review newReview = reviewRepo.save(review);
        return ResponseEntity.status(201).body(newReview);
    }

    /**
     * Updates an existing review in the database.
     * @param id The ID of the review to update
     * @param review The review that is to be updated.
     * @return ResponseEntity containing either:
     *      - 200 OK with the updated review.
     *      - 404 Not Found if the review doesn't exist.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable long id ,@RequestBody Review review) {
        if (!reviewRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        review.setId(id);
        return ResponseEntity.ok(reviewRepo.save(review));
    }

    /**
     * Deletes a review from the database
     * @param id The ID of the review to be deleted.
     * @return ResponseEntity containing either:
     *      - 204 No Content if deleted successfully
     *      - 404 Not Found if review doesn't exist
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) {
        if (!reviewRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reviewRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
