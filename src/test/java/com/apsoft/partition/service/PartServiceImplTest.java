package com.apsoft.partition.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PartServiceImplTest {

    public MultipartFile file;

    public PartService partService;

    @BeforeEach
    public void before() {
        file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getOriginalFilename()).thenReturn("file.pdf");
        partService = new PartServiceImpl();
    }

    @Test
    public void testShouldThrowException() throws IOException {
        assertThrows(UnsupportedOperationException.class, () -> partService.parseFile(file));
    }
}