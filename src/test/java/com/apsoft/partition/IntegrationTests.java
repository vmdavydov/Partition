package com.apsoft.partition;

import com.apsoft.partition.exceptions.WrongNestingSectionException;
import com.apsoft.partition.service.PartService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class IntegrationTests {

    public static final String PATH = "src/test/java/resources/testdata";

    @Autowired
    private PartService service;

    @Test
    public void shouldUploadFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("test.txt",
                new FileInputStream(PATH + "/test.txt"));
        List<String> list = service.parseFile(multipartFile, PATH);
        Assertions.assertNotNull(list);
    }

    @Test
    public void testShouldThrowException() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.txt",
                new FileInputStream(PATH + "/test.txt"));
        List<String> list = service.parseFile(multipartFile, PATH);
        assertThrows(WrongNestingSectionException.class, () -> service.printResult(list));
    }
    @AfterAll
    public static void clearDir() {
        File dir = new File(PATH);
        for (File myFile : Objects.requireNonNull(dir.listFiles())) {
            if (myFile.isFile() && !myFile.getName().equals("test.txt")) {
                myFile.delete();
            }
        }
    }
}