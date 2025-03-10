package org.example.petstore.repository;

import org.example.petstore.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PetRepository {

    private final List<Pet> pets = new ArrayList<>();

    public List<Pet> findAll() {
        return pets;
    }

    public Optional<Pet> findById(Long id) {
        return pets.stream().filter(pet -> pet.getId().equals(id)).findFirst();
    }

    public Pet save(Pet pet) {
        pets.add(pet);
        return pet;
    }

    public void deleteById(Long id) {
        pets.removeIf(pet -> pet.getId().equals(id));
    }
}