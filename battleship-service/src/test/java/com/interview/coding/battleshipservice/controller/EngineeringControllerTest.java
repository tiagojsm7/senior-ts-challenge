package com.interview.coding.battleshipservice.controller;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class EngineeringControllerTest {

    EngineeringController engineeringController;

    @Test
    public void testPing() {
        engineeringController = new EngineeringController();
        assertEquals(engineeringController.ping(), "pong");
    }

}
