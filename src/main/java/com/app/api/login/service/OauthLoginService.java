package com.app.api.login.service;

import com.app.api.login.dto.OauthLoginDto;
import com.app.domain.member.constant.MemberType;
import com.app.external.model.OAuthAttributes;
import com.app.external.oauth.kakao.service.SocialLoginApiServiceFactory;
import com.app.external.service.SocialLoginApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class OauthLoginService {

    public OauthLoginDto.Response oauthLogin(String accessToken, MemberType memberType) {
        //멤버 타입으로 구현체 획득
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
        //sns 로그인 정보 획득
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        return OauthLoginDto.Response.builder().build();
    }
}
