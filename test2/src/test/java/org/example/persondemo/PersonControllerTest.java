package org.example.persondemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetPersonById() {
        ResponseEntity<Person> response = restTemplate.getForEntity("/persons/1", Person.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testGetPersonByIdNotFound() {
        ResponseEntity<Person> response = restTemplate.getForEntity("/persons/999", Person.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddPersons() {
        List<Person> newPersons = Arrays.asList(
                new Person(3, "Alice", 22),
                new Person(4, "Bob", 33)
        );

        ResponseEntity<Void> response = restTemplate.postForEntity("/persons", newPersons, Void.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Проверка, что персоны добавились
        ResponseEntity<Person> aliceResponse = restTemplate.getForEntity("/persons/3", Person.class);
        assertEquals(HttpStatus.OK, aliceResponse.getStatusCode());
        assertEquals("Alice", aliceResponse.getBody().getName());
    }
}