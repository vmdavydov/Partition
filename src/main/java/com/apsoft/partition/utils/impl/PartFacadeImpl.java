package com.apsoft.partition.utils.impl;

import com.apsoft.partition.model.PartNode;
import com.apsoft.partition.service.PartService;
import com.apsoft.partition.utils.PartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PartFacadeImpl implements PartFacade {

    private final PartService service;
    @Value("${upload.path}")
    private String path;

    public String getResult(MultipartFile file, PartService service) {
        PartNode root = null;
        try {
            root = service.reformat(service.parseFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = service.printResult(root, service.getHeaders());
        service.deleteTemp(path);
        return result;
    }



}
