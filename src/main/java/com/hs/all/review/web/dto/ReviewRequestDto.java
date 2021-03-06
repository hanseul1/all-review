package com.hs.all.review.web.dto;

import com.hs.all.review.domain.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    private String title;
    private String content;
    private Long userId;

    @Builder
    public ReviewRequestDto(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Review toEntity() {
        return Review.builder()
          .title(title)
          .content(content)
          .userId(userId)
          .build();
    }
}
