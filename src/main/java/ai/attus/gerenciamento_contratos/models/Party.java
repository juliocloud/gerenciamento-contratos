package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.IdentificationType;
import ai.attus.gerenciamento_contratos.enums.PartyType;
import jakarta.persistence.*;
import lombok.Data;

//@Entity
public class Party {

    //@Id
    private String identification;

    private String fullName;
    private IdentificationType identificationType;

    private PartyType type;
    private Contact contact;

}
