package com.hs.all.review.web.controller;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> prodProfiles = Arrays.asList("production", "production1", "production2");

        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream()
          .filter(prodProfiles::contains)
          .findAny()
          .orElse(defaultProfile);
    }
}
