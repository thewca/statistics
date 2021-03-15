package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface AuthenticationController {

    @GetMapping("wca-url")
    String getWcaAuthenticationUrl(String frontendHost);
}
