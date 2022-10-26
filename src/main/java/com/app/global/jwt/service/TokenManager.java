package com.app.global.jwt.service;

import com.app.domain.member.constant.Role;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.constant.ToKenType;
import com.app.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    /**
     * JwtTokenDto 생성
     * @param memberId
     * @param role
     * @return
     */
    public JwtTokenDto createJwtTokenDto(Long memberId, Role role) {
        // 만료시간 생성
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        // 토큰 생성
        String accessToken = createAccessToken(memberId, role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(memberId, refreshTokenExpireTime);
        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    /**
     * accessToken 만료시간 반환
     * @return
     */
    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    /**
     * refreshToken 만료시간 반환
     * @return
     */
    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    /**
     * accessToken 생성
     */
    public String createAccessToken(Long memberId, Role role, Date expirationTime) {
        return Jwts.builder()
                .setSubject(ToKenType.ACCESS.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("memberId", memberId)      // 회원 아이디
                .claim("role", role)               // 유저 role
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))   // 토큰을 생성할때 사용할 알고리즘 지정 (HS512)
                .setHeaderParam("type", "JWT")  // 토큰 타입 JWT
                .compact();
    }

    /**
     * refreshToken 생성
     */
    public String createRefreshToken(Long memberId, Date expirationTime) {
        return Jwts.builder()
                .setSubject(ToKenType.REFRESH.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("memberId", memberId)      // 회원 아이디
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))   // 토큰을 생성할때 사용할 알고리즘 지정 (HS512)
                .setHeaderParam("type", "JWT")  // 토큰 타입 JWT
                .compact();
    }

    public void validateToken(String tokenSecret) {
        try {
            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJwt(tokenSecret);
        } catch (ExpiredJwtException e) {
            log.error("token 만료 ", e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.error("유효하지 않은 토큰 ", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

}
