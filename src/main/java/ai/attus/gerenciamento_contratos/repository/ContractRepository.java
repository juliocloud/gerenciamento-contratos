package ai.attus.gerenciamento_contratos.repository;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    List<Contract> findByStatus(ContractStatus status);

    // @Query("SELECT c FROM Contract c WHERE c.creationDate BETWEEN :start AND :end")
    // List<Contract> findByCreationDateRange(LocalDateTime start, LocalDateTime end);

    // @Query("SELECT c FROM contracts c WHERE c.number IN (SELECT p.contract_id FROM parties p WHERE p.identification = :identification)")
    // List<Contract> findByIdentification(String identification);
}
