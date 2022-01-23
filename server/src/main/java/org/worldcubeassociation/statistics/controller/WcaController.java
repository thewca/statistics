package org.worldcubeassociation.statistics.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;
import org.worldcubeassociation.statistics.response.AuthenticationResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
@RequestMapping("wca")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface WcaController {

    @GetMapping("authentication")
    AuthenticationResponse getWcaAuthenticationUrl(@Valid @NotBlank(message = "Frontend can not be blank") @RequestParam String frontendHost);

    @GetMapping("user")
    UserInfoDTO getUserInfo(@RequestHeader("Authorization") String token);
}
