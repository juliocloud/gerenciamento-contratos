package ai.attus.gerenciamento_contratos.repository;

import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.models.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, String> {
    @Query("SELECT p FROM Party p WHERE p.contractId = :contractNumber ")
    List<Party> findByContractNumber(String contractNumber);
}
