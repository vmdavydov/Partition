package com.apsoft.partition.controller;

import com.apsoft.partition.service.PartService;
import com.apsoft.partition.utils.facade.PartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PartController {

    private final PartService service;

    private final PartFacade facade;

    @PostMapping("/api/upload")
    public ResponseEntity<String> partition(@RequestParam("file") MultipartFile file) {
        String result = facade.getResult(file, service);
        return ResponseEntity.ok(result);
    }

}
