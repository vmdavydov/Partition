package com.apsoft.partition.utils.facade;

import com.apsoft.partition.service.PartService;
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
        String result = "";
        try {
            result = service.printResult(service.parseFile(file, path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.deleteTemp(path);
        return result;
    }
}
