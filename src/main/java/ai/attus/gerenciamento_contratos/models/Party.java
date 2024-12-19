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
    public Party(String identification, String fullName, IdentificationType identificationType, PartyType type, String email, String phone) {
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
    @NotNull(message = "Mandatory field")
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
    private String contractId;

    private IdentificationType identificationType;

    private PartyType type;


}
