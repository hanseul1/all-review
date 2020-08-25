package com.hs.all.review.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void return_hello_with_get_request() throws Exception {
        String expected = "hello all-review";

        mvc.perform(MockMvcRequestBuilders.get("/hello"))
           .andExpect(status().isOk())
           .andExpect(content().string(expected));
    }

    @Test
    public void return_hello_dto_with_get_request() throws Exception {
        String name = "test";
        int amount = 1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto")
        .param("name", name)
        .param("amount", String.valueOf(amount)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name", Matchers.is(name)))
           .andExpect(jsonPath("$.amount", Matchers.is(amount)));
    }
}
