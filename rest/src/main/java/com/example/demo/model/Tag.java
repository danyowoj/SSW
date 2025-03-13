package com.example.demo.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Tag(){}

    public Tag(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
