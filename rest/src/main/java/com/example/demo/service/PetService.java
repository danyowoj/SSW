package com.example.demo.service;

import com.example.demo.model.Pet;
import com.example.demo.model.Status;
import com.example.demo.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public String deletePet(Long id) {
        petRepository.deleteById(id);
        return "Pet with id " + id + " has been deleted";
    }

    public Pet updateById(Long id, String name, Status status) {
        Pet pet = findById(id);
        if (name != null) pet.setName(name);
        if (status != null) pet.setStatus(status);
        return petRepository.save(pet);
    }

    public Pet update(Pet pet) {
        return petRepository.save(pet);
    }
}
