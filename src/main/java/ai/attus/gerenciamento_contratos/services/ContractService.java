package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;
import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
            MakeFieldError fieldError = new MakeFieldError("number", "Duplicate contract number");
            throw new DuplicateFieldValueException(fieldError);
        }
        return contractRepository.save(contract);
    }

    @Transactional
    public Contract updateContract(String contractNumber, Contract updatedContract) {
        Contract existingContract = contractRepository.findById(contractNumber)
                .orElseThrow(() -> new IllegalArgumentException("Contract with number " + contractNumber + " not found"));

        if (updatedContract.getDescription() != null) {
            existingContract.setDescription(updatedContract.getDescription());
        }

        return contractRepository.save(existingContract);
    }

    @Transactional
    public Contract archiveContract(String contractNumber) {
        Contract existingContract = contractRepository.findById(contractNumber)
                .orElseThrow(() -> new IllegalArgumentException("Contract with number " + contractNumber + " not found"));

        existingContract.setStatus(ContractStatus.SUSPENDED);

        return contractRepository.save(existingContract);
    }


    @Transactional
    public List<Contract> searchByStatus(ContractStatus status) {
        return contractRepository.findByStatus(status);
    }

    @Transactional
    public List<Contract> searchByCreationDate(LocalDate date) {
        return contractRepository.findByCreationDate(date);
    }

    @Transactional
    public List<Contract> searchByIdentification(String identification) {
        return contractRepository.findByIdentification(identification);
    }


    public void fillAutomaticFields(Contract contract){
        contract.setCreationDate(LocalDate.now());
    }

    public boolean existsById(String contractId) {
        return contractRepository.existsById(contractId);
    }

    public void seal(ContractStatus status, String contractNumber){
        Optional<Contract> contract = contractRepository.findById(contractNumber);
        contract.ifPresent(value -> value.setStatus(status));
    }

    public Optional<Contract> findById(String id){
        return contractRepository.findById(id);
    }

}
