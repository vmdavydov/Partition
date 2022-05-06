package com.apsoft.partition.service;

import com.apsoft.partition.model.PartNode;
import com.apsoft.partition.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PartServiceImpl implements PartService {
    private static final Logger log = LoggerFactory.getLogger(PartServiceImpl.class);

    private static final List<String> headers = new ArrayList<>();

    private static final List<String> body = new ArrayList<>();

    @Value("${upload.path}")
    private String dir;

    private static String diff(String s) {
        OptionalInt first = IntStream.range(0, s.length()).filter(i -> s.charAt(i) != ' ').findFirst();
        return " ".repeat(Math.max(0, first.getAsInt()));
    }

    @Override
    public List<String> parseFile(MultipartFile file) throws IOException {
        Validation.getFileExtension(file);
        String uuidName = UUID.randomUUID().toString();
        String resultName = uuidName + "." + file.getOriginalFilename();
        String pathName = dir + "/" + resultName;
        file.transferTo(new File(pathName));
        List<String> lines = Files.readAllLines(new File(pathName).toPath(), StandardCharsets.UTF_8);
        lines.add(0, "Name of file");
        lines = lines.stream().map(s -> s.replace('#', ' ')).collect(Collectors.toList());
        log.info("Parse file: {}", file.getOriginalFilename());
        return lines;
    }

    @Override
    public PartNode reformat(List<String> input) {
        PartNode root = new PartNode(input.get(0));
        root.setParent(null);
        PartNode currentNode = root;
        for (int i = 1; i < input.size(); i++) {
            if (!input.get(i).startsWith(" ")) {
                currentNode.setName(currentNode.getName() + "\n" + diff(currentNode.getName()) + input.get(i));
                continue;
            }
            int cCount = input.get(i).length() - input.get(i).trim().length();
            int pCount = currentNode.getName().length() - currentNode.getName().trim().length();
            if (cCount > pCount) {
                PartNode node = new PartNode(input.get(i));
                node.setIndex(1);
                node.setParent(currentNode);
                node.setAIndex(node.getParent().getAIndex() == null ? String.valueOf(node.getIndex()) : node.getParent().getAIndex() + "." + node.getIndex());
                node.setName(node.getName() + " " + node.getAIndex());
                currentNode.addChild(node);
                currentNode = node;
                headers.add(node.getName());
            } else if (cCount == pCount) {
                PartNode node = new PartNode(input.get(i));
                node.setIndex(currentNode.getIndex() + 1);
                currentNode.getParent().addChild(node);
                node.setParent(currentNode.getParent());
                node.setAIndex(node.getParent().getAIndex() == null ? String.valueOf(node.getIndex()) : node.getParent().getAIndex() + "." + node.getIndex());
                node.setName(node.getName() + " " + node.getAIndex());
                currentNode = node;
                headers.add(node.getName());
            } else {
                PartNode node = new PartNode(input.get(i));
                node.setIndex(currentNode.getParent().getIndex() + 1);
                currentNode.getParent().getParent().addChild(node);
                node.setParent(currentNode.getParent().getParent());
                node.setAIndex(node.getParent().getAIndex() == null ? String.valueOf(node.getIndex()) : node.getParent().getAIndex() + "." + node.getIndex());
                node.setName(node.getName() + " " + node.getAIndex());
                currentNode = node;
                headers.add(node.getName());
            }
        }
        log.info("After reformatted");
        return root;
    }

    @Override
    public String printResult(PartNode root, List<String> headers) {
        StringBuilder sb = new StringBuilder();
        headers.forEach(str -> {
            sb.append(str);
            sb.append('\n');
        });
        sb.append('\n');
        recursiveDeep(root);
        body.forEach(str -> {
            sb.append(str);
            sb.append('\n');
        });
        log.info("Print into response");
        clear();
        return String.valueOf(sb);
    }

    @Override
    public List<String> getHeaders() {
        return headers;
    }

    public void recursiveDeep(PartNode root) {
        for (int i = 0; i < root.getChildren().size(); i++) {
            PartNode node = root.getChildren().get(i);
            System.out.println(node.getName());
            System.out.println();
            body.add(node.getName());
            if (!node.getChildren().isEmpty()) {
                recursiveDeep(node);
            }
        }
    }

    public void clear() {
        headers.clear();
        body.clear();
    }

}
