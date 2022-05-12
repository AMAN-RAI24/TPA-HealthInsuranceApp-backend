package com.insurecorp.insureCorp.controlllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/hw")
    public String helloWorld()
    {
        return "Hello World";
    }
}
