package com.apsoft.partition.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartNodeTest {

    @Test
    public void TestNodeParentOnNewCreated() {
        PartNode root = new PartNode("root");
        assertNull(root.getParent());
    }

    @Test
    public void TestNodeOnNewCreatedWithoutChildren() {
        PartNode root = new PartNode("root");
        assertEquals(root.getChildren().size(), 0);
    }

}