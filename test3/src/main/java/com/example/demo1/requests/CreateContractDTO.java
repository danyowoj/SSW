package com.example.demo1.requests;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateContractDTO {
    @NotBlank(message = "Contract number is required")
    public String contractNumber;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in present or future")
    public LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in future")
    public LocalDate endDate;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    public BigDecimal amount;

    public String description;

    @NotNull(message = "Client ID is required")
    public Long clientId;

    @NotNull(message = "Supplier ID is required")
    public Long supplierId;
}