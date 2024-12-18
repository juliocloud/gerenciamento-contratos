package ai.attus.gerenciamento_contratos.controllers;

import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.services.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<Contract> searchContract(@RequestBody Contract contract){
        System.out.println(contract);
        return ResponseEntity.ok(contractService.createContract(contract));
    }
}
