package com.example.demo.service;

import com.example.demo.model.Pet;
import com.example.demo.model.Status;
import com.example.demo.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class PetServiceTest {

    @Autowired
    private PetService petService;

    @MockBean
    private PetRepository petRepository;

    @Test
    public void testCreatePet() {
        Pet pet = new Pet(1L, "Buddy", null, Collections.emptyList(), Status.AVAILABLE);
        Mockito.when(petRepository.savePet(pet)).thenReturn(pet);

        Pet createdPet = petService.create(pet);
        assertEquals(pet, createdPet);
    }

    @Test
    public void testFindPetById() {
        Pet pet = new Pet(1L, "Buddy", null, Collections.emptyList(), Status.AVAILABLE);
        Mockito.when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Pet foundPet = petService.findById(1L);
        assertEquals(pet, foundPet);
    }

    @Test
    public void testFindAllPets() {
        Pet pet = new Pet(1L, "Buddy", null, Collections.emptyList(), Status.AVAILABLE);
        Mockito.when(petRepository.findAll()).thenReturn(Collections.singletonList(pet));

        List<Pet> pets = petService.findAllPets();
        assertEquals(1, pets.size());
        assertEquals(pet, pets.get(0));
    }

    @Test
    public void testDeletePet() {
        Pet pet = new Pet(1L, "Buddy", null, Collections.emptyList(), Status.AVAILABLE);
        Mockito.when(petRepository.deletePet(1L)).thenReturn(pet);

        Pet deletedPet = petService.deletePet(1L);
        assertEquals(pet, deletedPet);
    }

    @Test
    public void testUpdatePetById() {
        Pet pet = new Pet(1L, "Buddy", null, Collections.emptyList(), Status.AVAILABLE);
        Mockito.when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        Mockito.when(petRepository.savePet(pet)).thenReturn(pet);

        Pet updatedPet = petService.updateById(1L, "Buddy", Status.AVAILABLE);
        assertEquals(pet, updatedPet);
    }

    @Test
    public void testUpdatePet() {
        Pet pet = new Pet(1L, "Buddy", null, Collections.emptyList(), Status.AVAILABLE);
        Mockito.when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        Mockito.when(petRepository.savePet(pet)).thenReturn(pet);

        Pet updatedPet = petService.update(pet);
        assertEquals(pet, updatedPet);
    }
}
