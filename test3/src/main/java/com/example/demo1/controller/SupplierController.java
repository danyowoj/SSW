package com.example.demo1.controller;

import com.example.demo1.model.Supplier;
import com.example.demo1.repository.SupplierRepository;
import com.example.demo1.requests.CreateSupplierDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Suppliers", description = "Управление поставщиками")
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Operation(summary = "Получить всех поставщиков")
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return ResponseEntity.ok(suppliers);
    }

    @Operation(summary = "Создать нового поставщика")
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody @Valid CreateSupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.name);
        supplier.setAddress(supplierDTO.address);
        supplier.setContactPerson(supplierDTO.contactPerson);
        supplier.setPhone(supplierDTO.phone);
        supplier.setEmail(supplierDTO.email);

        Supplier savedSupplier = supplierRepository.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSupplier);
    }
}
