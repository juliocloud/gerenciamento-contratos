package ai.attus.gerenciamento_contratos.exceptions;

import ai.attus.gerenciamento_contratos.controllers.common.FieldError;

public class DuplicateFieldValueException extends RuntimeException {
    FieldError field;

    public DuplicateFieldValueException(String message, FieldError field) {
        super(message);
        this.field = field;
    }

    public FieldError getField() {
        return field;
    }
}
