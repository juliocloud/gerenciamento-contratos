package ai.attus.gerenciamento_contratos.exceptions;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;

public abstract class FieldException  extends RuntimeException{

    MakeFieldError field;

    public FieldException(MakeFieldError field) {
        super(field.error());
        this.field = field;
    }

    public MakeFieldError getField() {
        return field;
    }
}
