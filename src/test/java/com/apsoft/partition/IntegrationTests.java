package com.apsoft.partition;

import com.apsoft.partition.service.PartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IntegrationTests {

    @Autowired
    private PartService service;

    @Test
    public void shouldUploadFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("test.txt", new FileInputStream("src/test/java/resources/test.txt"));
        List<String> list = service.parseFile(multipartFile);
        assertThat(!list.isEmpty());
    }

}