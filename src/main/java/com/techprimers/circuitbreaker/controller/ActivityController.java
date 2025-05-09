package com.techprimers.circuitbreaker.controller;

import com.techprimers.circuitbreaker.model.Activity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequestMapping("/activity")
@RestController
public class ActivityController {

    private RestTemplate restTemplate;

    private final String BORED_API = "https://www.boredapi.com/api/activity";

    public ActivityController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    @CircuitBreaker(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    @Retry(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    public String getRandomActivity() {
        log.info("Calling Bored API");
//        ResponseEntity<Activity> responseEntity = restTemplate.getForEntity(BORED_API, Activity.class);
//        if (responseEntity.getStatusCode().is5xxServerError()) {
//            log.info("Error occurred while calling Bored API");
//            throw new HttpServerErrorException(responseEntity.getStatusCode());
//        }
        throw new RuntimeException("Simulated failure");
//        Activity activity = new Activity();
//        log.info("Activity received: " + activity.getActivity());
    }

    public String fallbackRandomActivity(Throwable throwable) {
        log.info("Fallback method called: " + throwable.getMessage());
        return "Watch a video from TechPrimers";
    }

}

