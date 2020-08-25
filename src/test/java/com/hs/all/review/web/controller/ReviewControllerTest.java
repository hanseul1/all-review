package com.hs.all.review.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.hs.all.review.domain.entity.Review;
import com.hs.all.review.domain.repository.ReviewRepository;
import com.hs.all.review.web.dto.ReviewRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReviewControllerTest {

    private static final String LOCALHOST_URL_PREFIX = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReviewRepository reviewRepository;

    @After
    public void cleanUp() throws Exception {
        reviewRepository.deleteAll();
    }

    @Test
    public void save_review() throws Exception {
        // given
        String title = "title";
        String content = "content";
        Long memberId = 1L;

        ReviewRequestDto requestDto = ReviewRequestDto.builder()
          .title(title)
          .content(content)
          .memberId(memberId)
          .build();

        String url = LOCALHOST_URL_PREFIX + port + "/api/v1/reviews";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList.get(0).getTitle()).isEqualTo(title);
        assertThat(reviewList.get(0).getContent()).isEqualTo(content);
        assertThat(reviewList.get(0).getMemberId()).isEqualTo(memberId);
    }

    @Test
    public void update_review() throws Exception {
        // given
        Review review = reviewRepository.save(Review.builder()
        .title("title")
        .content("content")
        .memberId(1L)
        .build());

        Long reviewId = review.getId();
        String updatedTitle = "UpdateTitle";
        String updatedContent = "UpdateContent";

        ReviewRequestDto requestDto = ReviewRequestDto.builder()
          .title(updatedTitle)
          .content(updatedContent)
          .build();

        String url = LOCALHOST_URL_PREFIX + port + "/api/v1/reviews/" + reviewId;

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList.get(0).getTitle()).isEqualTo(updatedTitle);
        assertThat(reviewList.get(0).getContent()).isEqualTo(updatedContent);
    }
}
