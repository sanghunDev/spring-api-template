package com.app.api.login.controller;

import com.app.api.login.dto.OauthLoginDto;
import com.app.api.login.validator.OauthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OauthLoginController {

    private final OauthValidator oauthValidator;

    @PostMapping("/login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(@RequestBody OauthLoginDto.Request oauthLoginRequestDto, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        oauthValidator.validateAuthorization(authorization);
        oauthValidator.validateMemberType(oauthLoginRequestDto.getMemberType());
        return ResponseEntity.ok(OauthLoginDto.Response.builder().build());
    }
}
