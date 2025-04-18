package com.example.demo1.service;

import com.example.demo1.model.Client;
import com.example.demo1.model.Contract;
import com.example.demo1.model.Supplier;
import com.example.demo1.repository.ClientRepository;
import com.example.demo1.repository.ContractRepository;
import com.example.demo1.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Transactional
    public Contract createContract(Contract contract, Long clientId, Long supplierId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        contract.setClient(client);
        contract.setSupplier(supplier);

        return contractRepository.save(contract);
    }
}
