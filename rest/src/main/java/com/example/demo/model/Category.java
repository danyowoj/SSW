package com.example.demo.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    public Category(){}

    public Category(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
