package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "events")
public class Event {

    public Event() {
    }

    public Event(String id, EventType type, LocalDate registrationDate, String description, String contractId) {
        this.id = id;
        this.type = type;
        this.registrationDate = registrationDate;
        this.description = description;
        this.contractId = contractId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Id
    @Column(name = "id", unique = true)
    @JsonIgnore
    private String id;

    @Column(name = "event_type")
    private EventType type;

    @Column(name = "event_date")
    private LocalDate registrationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "contract_id")
    private String contractId;

}
