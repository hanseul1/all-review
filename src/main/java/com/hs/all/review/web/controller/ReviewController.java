package com.hs.all.review.web.controller;

import com.hs.all.review.domain.service.ReviewService;
import com.hs.all.review.web.dto.ReviewRequestDto;
import com.hs.all.review.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<Long> saveReview(@RequestBody ReviewRequestDto requestDto) {
        return new ResponseEntity<Long>(reviewService.save(requestDto), HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> findReviewById(@PathVariable Long reviewId) {
        return new ResponseEntity<ReviewResponseDto>(reviewService.findById(reviewId), HttpStatus.OK);
    }

    @PostMapping("/{reviewId}")
    public ResponseEntity<Long> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto) {
        return new ResponseEntity<Long>(reviewService.update(reviewId, requestDto), HttpStatus.OK);
    }
}
