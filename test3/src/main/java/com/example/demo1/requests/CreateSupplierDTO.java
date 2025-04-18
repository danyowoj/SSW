package com.example.demo1.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateSupplierDTO {
    @NotBlank(message = "Name is required")
    public String name;

    public String address;
    public String contactPerson;

    @Pattern(regexp = "^\\+?[0-9\\s()-]+$", message = "Invalid phone format")
    public String phone;

    @Email(message = "Invalid email format")
    public String email;
}