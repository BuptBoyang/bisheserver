package indi.boyang.bishe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MainController {
    @GetMapping("/")
    List<String> listAPI() {
        return Arrays.asList("/videos","/videos/{id}");
    }
    @GetMapping("/test")
    String test(){ return "test"; }
}
