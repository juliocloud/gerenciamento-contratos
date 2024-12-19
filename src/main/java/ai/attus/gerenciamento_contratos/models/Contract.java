package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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
            @JsonProperty("creationDate") LocalDate creationDate,
            @JsonProperty("description") String description,
            @JsonProperty("status") ContractStatus status) {
        this.number = number;
        this.creationDate = creationDate;
        this.description = description;
        this.status = status;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public Contract(
            LocalDate creationDate,
            String description,
            ContractStatus status) {

        this.creationDate = creationDate;
        this.description = description;
        this.status = status;
    }

    @Id
    @Column(name = "contract_number", unique = true)
    private String number;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    public String getNumber() {
        return number;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    public ContractStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Version
    @JsonIgnore
    private int version;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Party> parties;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;
}
