package com.example.docker;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInfoController {

    @RequestMapping(value = "/build-info", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ClassPathResource> version() {
        return ResponseEntity.ok()
                .body(new ClassPathResource("META-INF/build-info.properties"));
    }
}