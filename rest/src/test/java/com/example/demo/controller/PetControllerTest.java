package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Pet;
import com.example.demo.model.Status;
import com.example.demo.model.Tag;
import com.example.demo.service.PetService;
import com.example.demo.validation.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @MockBean
    private Validation validation;

    @Test
    public void testAddPet() throws Exception {
        Category category = new Category(1L, "Dogs");
        Tag tag = new Tag(1L, "Friendly");
        Pet pet = new Pet(1L, "Buddy", category, Collections.singletonList(tag), Status.AVAILABLE);

        Mockito.when(validation.validatePet(pet)).thenReturn("Success");
        Mockito.when(petService.create(pet)).thenReturn(pet);

        mockMvc.perform(post("/pets/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Buddy\",\"category\":{\"id\":1,\"name\":\"Dogs\"},\"tags\":[{\"id\":1,\"name\":\"Friendly\"}],\"status\":\"AVAILABLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    public void testUpdatePet() throws Exception {
        Category category = new Category(1L, "Dogs");
        Tag tag = new Tag(1L, "Friendly");
        Pet pet = new Pet(1L, "Buddy", category, Collections.singletonList(tag), Status.AVAILABLE);

        Mockito.when(validation.validatePet(pet)).thenReturn("Success");
        Mockito.when(validation.validateID(1L)).thenReturn("Success");
        Mockito.when(petService.update(pet)).thenReturn(pet);

        mockMvc.perform(put("/pets/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Buddy\",\"category\":{\"id\":1,\"name\":\"Dogs\"},\"tags\":[{\"id\":1,\"name\":\"Friendly\"}],\"status\":\"AVAILABLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    public void testGetPetById() throws Exception {
        Category category = new Category(1L, "Dogs");
        Tag tag = new Tag(1L, "Friendly");
        Pet pet = new Pet(1L, "Buddy", category, Collections.singletonList(tag), Status.AVAILABLE);

        Mockito.when(petService.findById(1L)).thenReturn(pet);

        mockMvc.perform(get("/pets/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    public void testGetPets() throws Exception {
        Category category = new Category(1L, "Dogs");
        Tag tag = new Tag(1L, "Friendly");
        Pet pet = new Pet(1L, "Buddy", category, Collections.singletonList(tag), Status.AVAILABLE);

        Mockito.when(petService.findAllPets()).thenReturn(Collections.singletonList(pet));

        mockMvc.perform(get("/pets/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }

    @Test
    public void testDeletePet() throws Exception {
        Category category = new Category(1L, "Dogs");
        Tag tag = new Tag(1L, "Friendly");
        Pet pet = new Pet(1L, "Buddy", category, Collections.singletonList(tag), Status.AVAILABLE);

        Mockito.when(petService.deletePet(1L)).thenReturn(pet);

        mockMvc.perform(delete("/pets/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    public void testUpdatePetWithForm() throws Exception {
        Category category = new Category(1L, "Dogs");
        Tag tag = new Tag(1L, "Friendly");
        Pet pet = new Pet(1L, "Buddy", category, Collections.singletonList(tag), Status.AVAILABLE);

        Mockito.when(petService.updateById(1L, "Buddy", Status.AVAILABLE)).thenReturn(pet);

        mockMvc.perform(post("/pets/pet/1")
                        .param("name", "Buddy")
                        .param("status", "AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }
}