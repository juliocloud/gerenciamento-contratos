package ai.attus.gerenciamento_contratos.controllers;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.services.ContractService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody @Valid Contract contract) {
        var created = contractService.createContract(contract);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/archive")
    public ResponseEntity<Void> archiveContract(@RequestBody Map<String, String> request) {
        String contractNumber = request.get("contractNumber");
        contractService.archiveContract(contractNumber);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{contractNumber}")
    public ResponseEntity<Contract> updateContract(
            @PathVariable String contractId,
            @RequestBody @Valid Contract updatedContract) {
        var updated = contractService.updateContract(contractId, updatedContract);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Contract>> searchByStatus(@PathVariable ContractStatus status) {
        return ResponseEntity.ok(contractService.searchByStatus(status));
    }
}
