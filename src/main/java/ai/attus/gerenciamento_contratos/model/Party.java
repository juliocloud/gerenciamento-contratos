package ai.attus.gerenciamento_contratos.model;

import ai.attus.gerenciamento_contratos.enums.IdentificationType;
import ai.attus.gerenciamento_contratos.enums.PartyType;

public class Party {
    private String fullName;
    private String identification;
    private IdentificationType identificationType;
    private PartyType type;
    private Contact contact;

}
