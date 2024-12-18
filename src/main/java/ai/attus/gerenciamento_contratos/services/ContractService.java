package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional
    public Contract createContract(Contract contract) {
        try {
            return contractRepository.save(contract);
        } catch (ObjectOptimisticLockingFailureException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(">>>>>>>>>>");
        }
    }
}
