package com.example.demo.model;

import lombok.Data;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "pet_tag",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Pet() {
    }

    public Pet(Long id, String name, Category category, List<Tag> tags, Status status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.tags = tags;
        this.status = status;
    }
}
