package com.apsoft.partition.service;

import com.apsoft.partition.model.PartNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PartService {
    List<String> parseFile(MultipartFile file) throws IOException;

    PartNode reformat(List<String> input);

    String printResult(PartNode root, List<String> headers);

    List<String> getHeaders();

    void deleteTemp(String path);
}
