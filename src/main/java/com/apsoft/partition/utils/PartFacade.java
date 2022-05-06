package com.apsoft.partition.utils;

import com.apsoft.partition.service.PartService;
import org.springframework.web.multipart.MultipartFile;

public interface PartFacade {
    String getResult(MultipartFile file, PartService service);
}
