package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.IdentificationType;
import ai.attus.gerenciamento_contratos.enums.PartyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "parties")
public class Party {

    public Party(String id, String identification, String fullName, IdentificationType identificationType, PartyType type, String email, String phone) {
        this.id = id;
        this.identification = identification;
        this.fullName = fullName;
        this.identificationType = identificationType;
        this.type = type;
        this.email = email;
        this.phone = phone;
    }

    public Party() {
    }

    @Id
    @Column(name = "id", unique = true)
    @NotNull(message = "Party id cannot be null")
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "identification")
    private String identification;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "contract_id")
    @NotNull(message = "Contract id cannot be null")
    private String contractId;

    private IdentificationType identificationType;

    private PartyType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

}
