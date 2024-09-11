package com.flink.flink.controller;

import com.flink.flink.service.FlinkInitiationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flink")
@RequiredArgsConstructor
@Slf4j
public class FlinkJobContoller {

    public final FlinkInitiationService flinkInitiationService;

    @GetMapping("/start")
    public ResponseEntity start() {
        flinkInitiationService.start();
        log.info("flink job initiated");
        return ResponseEntity.accepted().build();
    }
}
