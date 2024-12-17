package com.arturfrimu.prometheus.grafana;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/monitor")
    public String monitor() {
        return "Hello world";
    }
}
