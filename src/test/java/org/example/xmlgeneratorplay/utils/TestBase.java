package org.example.xmlgeneratorplay.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class TestBase {

    @Autowired
    private TestRestTemplate testRestTemplate;

    public <T> ResponseEntity<List<T>> runRequestFromFile(final String filename, final String url,
                                                          final HttpMethod method) throws IOException {
        //Read the JSON content from the file
        String body = new String(Files.readAllBytes(Path.of("src/test/resources/__files/request/" + filename + ".json")));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ParameterizedTypeReference<List<T>> typeRef = new ParameterizedTypeReference<>() {};

        return testRestTemplate.exchange(url,
                method,
                new HttpEntity<>(body, httpHeaders),
                typeRef);
    }

    public ResponseEntity<String> runRequestEmptyInput(final String url, final HttpMethod method) {
        String emptyJsonInput = "[]";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(emptyJsonInput, headers);

        return testRestTemplate.exchange(url, method, request, String.class);
    }
}