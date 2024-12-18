package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @Column(name = "contract_number", unique = true)
    private String number;


    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Override
    public String toString() {
        return "Contract{" +
                "number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Version
    private int version;

    public Contract(){

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
//@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Party> parties;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Event> events;
}
