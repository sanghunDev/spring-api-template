package com.app.global.config.web;

import com.app.global.intercepter.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    //cors 허용
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")              //허용 url 설정
                .allowedOrigins("*")      // 허용 origins (* : 모두 , 여러개는 , 로 구분해서 설정 가능)
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                )                                               //허용할 method 목록
                .maxAge(3600);                                  //프리플라이트 재요청 시간 변경
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1)                   //인터셉터 실행순서 (인증이 가장 먼저 동작)
                .addPathPatterns("/api/**") //어떤 요청에 적용시킬건가
                .excludePathPatterns(
                        "/api/oauth/login",
                        "/api/access-token/issue",
                        "/api/logout",
                        "/api/health");      //인터셉터 적용 예외 요청
    }
}
