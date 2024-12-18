package ai.attus.gerenciamento_contratos.repository;

import ai.attus.gerenciamento_contratos.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
}
