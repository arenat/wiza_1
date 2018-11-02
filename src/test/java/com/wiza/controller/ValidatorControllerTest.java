package com.wiza.controller;

import io.dropwizard.testing.junit.DropwizardClientRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.*;

public class ValidatorControllerTest {

    @ClassRule
    public static final DropwizardClientRule dropwizard = new DropwizardClientRule(new ValidatorController());

    @Test
    public void checkMaxConstrains() throws IOException {
        final URL url = new URL(dropwizard.baseUri() + "/validator/max?m=100");
        final String response = new BufferedReader(new InputStreamReader(url.openStream())).readLine();
        assertEquals("100", response);
    }
}