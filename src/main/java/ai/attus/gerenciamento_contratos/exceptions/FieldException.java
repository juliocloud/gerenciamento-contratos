package ai.attus.gerenciamento_contratos.exceptions;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;

import java.io.Serializable;

public abstract class FieldException  extends RuntimeException implements Serializable {

    protected final MakeFieldError field;

    protected FieldException(MakeFieldError field) {
        super(field.error());
        this.field = field;
    }

    public MakeFieldError getField() {
        return field;
    }
}
