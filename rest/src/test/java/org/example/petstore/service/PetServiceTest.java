package org.example.petstore.service;

import org.example.petstore.model.Pet;
import org.example.petstore.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetServiceTest {

    @Test
    public void testGetPetById() {
        PetRepository petRepository = Mockito.mock(PetRepository.class);
        PetService petService = new PetService(petRepository);

        Pet pet = new Pet();
        pet.setId(1L);
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> result = petService.getPetById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }
}