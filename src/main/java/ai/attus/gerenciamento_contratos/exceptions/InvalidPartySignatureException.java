package ai.attus.gerenciamento_contratos.exceptions;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;

public class InvalidPartySignatureException extends FieldException {
    public InvalidPartySignatureException(MakeFieldError field) {
        super(field);
    }
}

