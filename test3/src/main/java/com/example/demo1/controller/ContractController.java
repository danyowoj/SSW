package com.example.demo1.controller;

import com.example.demo1.model.Client;
import com.example.demo1.model.Contract;
import com.example.demo1.model.Supplier;
import com.example.demo1.repository.ClientRepository;
import com.example.demo1.repository.ContractRepository;
import com.example.demo1.repository.SupplierRepository;
import com.example.demo1.requests.CreateContractDTO;
import com.example.demo1.responses.ContractDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Contracts", description = "Управление договорами")
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Operation(summary = "Получить все договоры")
    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        List<ContractDTO> dtos = contracts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Создать новый договор")
    @PostMapping
    public ResponseEntity<?> createContract(@RequestBody @Valid CreateContractDTO contractDTO) {
        Optional<Client> client = clientRepository.findById(contractDTO.clientId);
        Optional<Supplier> supplier = supplierRepository.findById(contractDTO.supplierId);

        if (client.isEmpty() || supplier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Client or Supplier not found");
        }

        Contract contract = new Contract();
        contract.setContractNumber(contractDTO.contractNumber);
        contract.setStartDate(contractDTO.startDate);
        contract.setEndDate(contractDTO.endDate);
        contract.setAmount(contractDTO.amount);
        contract.setDescription(contractDTO.description);
        contract.setClient(client.get());
        contract.setSupplier(supplier.get());

        Contract savedContract = contractRepository.save(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedContract));
    }

    private ContractDTO convertToDto(Contract contract) {
        ContractDTO dto = new ContractDTO();
        dto.id = contract.getId();
        dto.contractNumber = contract.getContractNumber();
        dto.startDate = contract.getStartDate();
        dto.endDate = contract.getEndDate();
        dto.amount = contract.getAmount();
        dto.description = contract.getDescription();
        dto.clientId = contract.getClient().getId();
        dto.supplierId = contract.getSupplier().getId();
        dto.clientName = contract.getClient().getName();
        dto.supplierName = contract.getSupplier().getName();
        return dto;
    }
}
