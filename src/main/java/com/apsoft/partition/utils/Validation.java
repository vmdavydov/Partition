package com.apsoft.partition.utils;

import com.apsoft.partition.exceptions.NotSupportedFormatException;
import org.springframework.web.multipart.MultipartFile;

public class Validation {

    public static void getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!extension.equals("txt")) {
                throw new NotSupportedFormatException();
            }
        }

    }
}
