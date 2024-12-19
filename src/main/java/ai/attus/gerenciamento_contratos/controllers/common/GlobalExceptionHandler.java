package ai.attus.gerenciamento_contratos.controllers.common;

import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.exceptions.InvalidContractStatusException;
import ai.attus.gerenciamento_contratos.exceptions.InvalidPartySignatureException;
import ai.attus.gerenciamento_contratos.exceptions.ReferencedObjectDoesntExistException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<MakeFieldError> listErrors = fieldErrors.stream().map(
                fieldError -> new MakeFieldError(fieldError.getField(), fieldError.getDefaultMessage())
        ).collect(Collectors.toList());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", listErrors);
    }


    @ExceptionHandler(DuplicateFieldValueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleValidationException(DuplicateFieldValueException ex) {
        List<MakeFieldError> fieldErrors = new ArrayList<MakeFieldError>();
        fieldErrors.add(ex.getField());
        return new ResponseError(HttpStatus.CONFLICT.value(), "Conflict error", fieldErrors);
    }


    @ExceptionHandler(InvalidContractStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError hanldeInvalidContractStatus(InvalidContractStatusException ex) {
        List<MakeFieldError> fieldErrors = new ArrayList<MakeFieldError>();
        fieldErrors.add(ex.getField());
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Invalid value",fieldErrors);
    }

    @ExceptionHandler(ReferencedObjectDoesntExistException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleReferencedNonExistingObject(ReferencedObjectDoesntExistException ex) {
        List<MakeFieldError> fieldErrors = new ArrayList<MakeFieldError>();
        fieldErrors.add(ex.getField());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Referencing non existing object",fieldErrors);
    }

    @ExceptionHandler(InvalidPartySignatureException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleInvalidPartySignature(InvalidPartySignatureException ex) {
        List<MakeFieldError> fieldErrors = new ArrayList<MakeFieldError>();
        fieldErrors.add(ex.getField());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid party signature",fieldErrors);
    }

}
