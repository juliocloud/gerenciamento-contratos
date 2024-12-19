package ai.attus.gerenciamento_contratos.exceptions;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;

public class InvalidContractStatusException extends FieldException {
    public InvalidContractStatusException(MakeFieldError field) {
        super(field);
    }
}

