package org.worldcubeassociation.statistics.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;

@Validated
@RequestMapping("wca")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface WcaController {

    @GetMapping("user")
    UserInfoDTO getUserInfo(@RequestHeader(value = "Authorization", required = false) String token);
}
