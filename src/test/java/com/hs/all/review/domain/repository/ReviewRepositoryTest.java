package com.hs.all.review.domain.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.hs.all.review.domain.entity.Review;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @After
    public void cleanUp() {
        reviewRepository.deleteAll();
    }

    @Test
    public void save_and_get_first_review() {
        // given
        reviewRepository.save(Review.builder()
        .title("title")
        .content("content")
        .userId(1L)
        .build());

        // when
        List<Review> reviewList = reviewRepository.findAll();

        // then
        Review review = reviewList.get(0);
        assertThat(review.getTitle(), is("title"));
        assertThat(review.getContent(), is("content"));
        assertThat(review.getUserId(), is(1L));
    }
}
