package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class Event {

    public Event() {
    }

    public Event(String id, EventType type, LocalDateTime registrationDate, String description, String contractId) {
        this.id = id;
        this.type = type;
        this.registrationDate = registrationDate;
        this.description = description;
        this.contractId = contractId;
    }

    @Id
    @Column(name = "id", unique = true)
    @NotNull(message = "Mandatory field")
    private String id;

    @Column(name = "event_type")
    private EventType type;

    @Column(name = "event_date")
    private LocalDateTime registrationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "contract_id")
    private String contractId;

}
