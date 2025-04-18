package com.example.demo1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import com.example.demo1.model.Contract;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String contactPerson;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Contract> contracts = new ArrayList<>();
}
