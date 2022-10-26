package com.app.api.login.controller;

import com.app.api.login.dto.OauthLoginDto;
import com.app.api.login.service.OauthLoginService;
import com.app.api.login.validator.OauthValidator;
import com.app.domain.member.constant.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OauthLoginController {

    private final OauthLoginService oauthLoginService;
    private final OauthValidator oauthValidator;

    @PostMapping("/login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(@RequestBody OauthLoginDto.Request oauthLoginRequestDto, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        oauthValidator.validateAuthorization(authorization);
        oauthValidator.validateMemberType(oauthLoginRequestDto.getMemberType());
        //토큰 획득
        String accessToken = authorization.split(" ")[1];

        OauthLoginDto.Response jwtTokenResponseDto = oauthLoginService.oauthLogin(accessToken, MemberType.from(oauthLoginRequestDto.getMemberType()));
        return ResponseEntity.ok(jwtTokenResponseDto);
    }
}
