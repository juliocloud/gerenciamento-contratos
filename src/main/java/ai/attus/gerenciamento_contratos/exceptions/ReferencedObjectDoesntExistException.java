package ai.attus.gerenciamento_contratos.exceptions;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;

public class ReferencedObjectDoesntExistException extends FieldException{
    public ReferencedObjectDoesntExistException(MakeFieldError field) {
        super(field);
    }
}
