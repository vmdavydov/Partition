package com.apsoft.partition;

import com.apsoft.partition.service.PartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

@SpringBootTest
public class IntegrationTests {

    @Autowired
    private PartService service;

    @Test
    public void shouldUploadFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("test.txt", new FileInputStream("src/test/java/resources/test.txt"));
        List<String> list = service.parseFile(multipartFile, "src/test/java/resources/");
        Assertions.assertNotNull(list);
    }

}