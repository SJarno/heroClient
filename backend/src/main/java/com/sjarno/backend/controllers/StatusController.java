package com.sjarno.backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjarno.backend.models.responses.GenericResponse;
import com.sjarno.backend.services.HeroService;

@RestController
public class StatusController {

    @Autowired
    private HeroService heroService;

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "ok");
        return status;
    }

    @GetMapping("/service-call")
    public GenericResponse<Map<String, String>> getServiceStatus() {
        return this.heroService.getStatus();
    }

    @GetMapping("/main-title")
    public GenericResponse<String> getServiceMainTitle() {
        return this.heroService.getTitle();
    }
}
