package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;

import javax.validation.constraints.NotBlank;

@RequestMapping("wca")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface WCAController {

    @GetMapping("authentication")
    String getWcaAuthenticationUrl(@NotBlank String frontendHost);

    @GetMapping("user")
    UserInfoDTO getUserInfo(@RequestHeader("Authorization") String token);
}
