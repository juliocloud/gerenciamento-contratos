package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "contracts")
public class Contract {

    public Contract(){

    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }


    @JsonCreator
    public Contract(
            @JsonProperty("number") String number,
            @JsonProperty("creationDate") LocalDateTime creationDate,
            @JsonProperty("description") String description,
            @JsonProperty("status") ContractStatus status) {
        this.number = number;
        this.creationDate = creationDate;
        this.description = description;
        this.status = status;
    }

    public Contract(
            LocalDateTime creationDate,
            String description,
            ContractStatus status) {

        this.creationDate = creationDate;
        this.description = description;
        this.status = status;
    }

    @Id
    @Column(name = "contract_number", unique = true)
    @NotNull(message = "Mandatory field")
    private String number;

    @Column(name = "creation_date")
    @Past(message = "Cannot be a future date")
    private LocalDateTime creationDate;

    @Column(name = "description")
    @NotNull(message = "Mandatory field")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    public String getNumber() {
        return number;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    public ContractStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Version
    @JsonIgnore
    private int version;

    @Override
    public String toString() {
        return "Contract{" +
                "number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Party> parties;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;
}
