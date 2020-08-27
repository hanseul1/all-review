package com.hs.all.review.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.all.review.domain.entity.Review;
import com.hs.all.review.domain.repository.ReviewRepository;
import com.hs.all.review.web.dto.ReviewRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReviewControllerTest {

    private static final String LOCALHOST_URL_PREFIX = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .build();
    }

    @After
    public void cleanUp() throws Exception {
        reviewRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void save_review() throws Exception {
        // given
        String title = "title";
        String content = "content";
        Long userId = 1L;

        ReviewRequestDto requestDto = ReviewRequestDto.builder()
          .title(title)
          .content(content)
          .userId(userId)
          .build();

        String url = LOCALHOST_URL_PREFIX + port + "/api/v1/reviews";

        // when
        mvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
           .andExpect(status().isOk());


        // then
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList.get(0).getTitle()).isEqualTo(title);
        assertThat(reviewList.get(0).getContent()).isEqualTo(content);
        assertThat(reviewList.get(0).getUserId()).isEqualTo(userId);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void update_review() throws Exception {
        // given
        Review review = reviewRepository.save(Review.builder()
        .title("title")
        .content("content")
        .userId(1L)
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
        mvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
           .andExpect(status().isOk());

        // then
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList.get(0).getTitle()).isEqualTo(updatedTitle);
        assertThat(reviewList.get(0).getContent()).isEqualTo(updatedContent);
    }
}
