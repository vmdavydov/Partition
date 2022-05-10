package com.apsoft.partition.service;

import com.apsoft.partition.exceptions.WrongNestingSectionException;
import com.apsoft.partition.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class PartServiceImpl implements PartService {

    private static final Logger log = LoggerFactory.getLogger(PartServiceImpl.class);

    public static final Map<Integer, Integer> secVal = new HashMap<>();
    public static final Set<Integer> keySection = new HashSet<>();
    public static String temp = "";

    @Override
    public List<String> parseFile(MultipartFile file, String dir) throws IOException {
        Validation.getFileExtension(file);
        String uuidName = UUID.randomUUID().toString();
        String resultName = uuidName + "." + file.getOriginalFilename();
        String pathName = dir + "/" + resultName;
        file.transferTo(new File(pathName));
        List<String> lines = Files.readAllLines(new File(pathName).toPath(), StandardCharsets.UTF_8);
        log.info("Parse file: {}", file.getOriginalFilename());
        return lines;
    }

    @Override
    public String printResult(List<String> lines) {
        StringBuilder header = new StringBuilder();
        StringBuilder body = new StringBuilder();
        lines.stream().filter(s -> s.startsWith("#")).forEach(s -> header.append(getValue(s)));
        header.append("\n");
        clearDataSec();
        lines.forEach(s -> body.append(getValue(s)));
        return String.valueOf(header.append(body));
    }

    private void clearDataSec() {
        secVal.clear();
        keySection.clear();
    }

    @Override
    public void deleteTemp(String path) {
        for (File myFile : new File(path).listFiles()) {
            if (myFile.isFile()) {
                myFile.delete();
            }
        }
        clearDataSec();
    }

    private static String diff(String s) {
        OptionalInt first = IntStream.range(0, s.length()).filter(i -> s.charAt(i) != ' ').findFirst();
        if (first.isPresent()) {
            return " ".repeat(Math.max(0, first.getAsInt()) - 1);
        }
        throw new NullPointerException();
    }

    public static String getValue(String sharp) {
        if (sharp.startsWith("#")) {
            temp = sharp.replace("#", " ");
            return recursiveGetSection(sharp, 0);
        } else {
            return "\n" + diff(temp) + sharp;
        }
    }

    private static String recursiveGetSection(String sharp, int section) {
        if (sharp.startsWith("#")) {
            return recursiveGetSection(sharp.substring(1), section + 1);
        } else {
            setupKeySection(section);
            return "\n" + " ".repeat(Math.max(0, section) - 1) + sharp + " " + getIndex(section);
        }
    }

    private static String getIndex(int section) {
        String value = "";
        if (section > 1) {
            value = getIndex(section - 1) + ".";
        }
        return value + Optional.ofNullable(secVal.get(section))
                .orElseThrow(WrongNestingSectionException::new);
    }

    private static void setupKeySection(int section) {
        secVal.merge(section, 1, Integer::sum);
        if (keySection.contains(section)) {
            keySection.forEach(key -> {
                if (key > section) secVal.remove(key);
            });
        } else {
            keySection.add(section);
        }
    }
}
