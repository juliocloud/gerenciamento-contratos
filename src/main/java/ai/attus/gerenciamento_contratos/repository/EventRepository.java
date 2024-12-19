package ai.attus.gerenciamento_contratos.repository;

import ai.attus.gerenciamento_contratos.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByContractId(String contractNumber);
}
