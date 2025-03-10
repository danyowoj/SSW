package org.example.persondemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")

public class PersonController {

    private List<Person> persons = new ArrayList<>();

    // Инициализация списка заготовленных персон
    public PersonController() {
        persons.add(new Person(1, "John Doe", 30));
        persons.add(new Person(2, "Jane Doe", 25));
    }

    // GET /personsё
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(persons);
    }

    // GET /persons/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        Optional<Person> person = persons.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        return person.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST /persons
    @PostMapping
    public ResponseEntity<Void> addPersons(@RequestBody List<Person> newPersons) {
        persons.addAll(newPersons);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
