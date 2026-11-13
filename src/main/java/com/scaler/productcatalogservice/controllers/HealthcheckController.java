package com.scaler.productcatalogservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {
    @GetMapping("/health")
    public String healthcheck() {
        return "OK";
    }
}
