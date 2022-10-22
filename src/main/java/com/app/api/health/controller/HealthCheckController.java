package com.app.api.health.controller;

import com.app.api.health.dto.HealthCheckResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthCheckController {

    private final Environment environment;

    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponseDto> healthCheck() {

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HealthCheckResponseDto healthCheckResponseDto = HealthCheckResponseDto.builder()
                .health("ok")
                .activeProfiles(Arrays.asList(environment.getActiveProfiles()))  //현재 activeProfiles 조회 (개발, 운영 등..)
                .build();
        return ResponseEntity.ok(healthCheckResponseDto);// status 200 , body 담겨서 반환
    }
}
