package ai.attus.gerenciamento_contratos.exceptions;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;

public class DuplicateFieldValueException extends FieldException {
    public DuplicateFieldValueException(MakeFieldError field) {
        super(field);
    }
}
