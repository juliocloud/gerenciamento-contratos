package ai.attus.gerenciamento_contratos.repository;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    List<Contract> findByStatus(ContractStatus status);

    @Query("SELECT c FROM Contract c WHERE c.creationDate = :date ")
    List<Contract> findByCreationDateRange(LocalDate date);

    @Query("SELECT c FROM Contract c WHERE c.number IN (SELECT p.contractId FROM Party p WHERE p.id = :identification)")
    List<Contract> findByIdentification(String identification);
}
