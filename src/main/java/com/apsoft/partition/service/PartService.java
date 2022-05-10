package com.apsoft.partition.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PartService {
    List<String> parseFile(MultipartFile file, String dir) throws IOException;

    String printResult(List<String> lines);

    void deleteTemp(String path);
}
