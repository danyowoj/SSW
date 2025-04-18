package com.example.demo1.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractDTO {
    public Long id;
    public String contractNumber;
    public LocalDate startDate;
    public LocalDate endDate;
    public BigDecimal amount;
    public String description;
    public Long clientId;
    public Long supplierId;
    public String clientName;
    public String supplierName;
}
