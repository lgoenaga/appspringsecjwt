package com.iudigital.appspringsecjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rute")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping("/home")
    public String welcome() {
        return "Welcome to the home page! Protected by JWT";
    }
}
