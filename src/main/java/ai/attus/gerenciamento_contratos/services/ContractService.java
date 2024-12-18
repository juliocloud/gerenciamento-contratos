package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.FieldError;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional
    public Contract createContract(Contract contract) throws DuplicateFieldValueException {
        fillAutomaticFields(contract);
        if(contractRepository.existsById(contract.getNumber())) {
            FieldError fieldError = new FieldError("number", "Duplicate contract number");
            throw new DuplicateFieldValueException("Duplicate contract number", fieldError);
        }
        return contractRepository.save(contract);
    }

    public void fillAutomaticFields(Contract contract){
        contract.setCreationDate(LocalDateTime.now());
    }
}
