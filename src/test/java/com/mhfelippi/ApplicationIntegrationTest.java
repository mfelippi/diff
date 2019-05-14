package com.mhfelippi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class ApplicationIntegrationTest {

    RestTemplate restTemplate = new RestTemplate();

    @Before
    public void setUp() {
        Application.main(new String[0]);

        restTemplate.put("http://localhost:8080/v1/diff/1/left", "eyJoZWxsbyI6ICJ3b3JsZCJ9\n");
        restTemplate.put("http://localhost:8080/v1/diff/1/right", "eyJoZWxsbyI6ICJ3b3JsZCJ9\n");


    }

    @After
    public void tearDown() {
        restTemplate.delete("http://localhost:8080/v1/diff/1/left");
        restTemplate.delete("http://localhost:8080/v1/diff/1/right");
    }

    @Test
    public void test() {
        ResponseEntity<DiffResult> entity= restTemplate.getForEntity("http://localhost:8080/v1/diff/1/", DiffResult.class);
        DiffResult result = entity.getBody();

        assertEquals(DiffResult.EQUAL, result);
    }

}
