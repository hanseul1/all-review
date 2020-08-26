package com.hs.all.review.web.dto;

import com.hs.all.review.domain.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long userId;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.userId = review.getUserId();
    }
}
