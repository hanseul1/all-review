package com.hs.all.review.domain.service;

import com.hs.all.review.domain.entity.Review;
import com.hs.all.review.domain.repository.ReviewRepository;
import com.hs.all.review.web.dto.ReviewRequestDto;
import com.hs.all.review.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(ReviewRequestDto requestDto) {
        return reviewRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long reviewId, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
          .orElseThrow(()-> new IllegalArgumentException("해당 리뷰가 없습니다. review Id = " + reviewId));

        review.update(requestDto.getTitle(), requestDto.getContent());

        return reviewId;
    }

    public ReviewResponseDto findById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
          .orElseThrow(()-> new IllegalArgumentException("해당 리뷰가 없습니다. review Id = " + reviewId));

        return new ReviewResponseDto(review);
    }
}
