package com.apsoft.partition.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void testShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> partService.parseFile(file,"dir"));
    }
}