package com.apsoft.partition.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PartNode {
    private String name;
    private List<PartNode> children = new ArrayList<>();
    private PartNode parent;
    private int index;
    private String aIndex;

    public PartNode(String word) {
        this.name = word;
    }
    public void addChild(PartNode child) {
        children.add(child);
    }
}