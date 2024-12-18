package ai.attus.gerenciamento_contratos.controllers.common;

import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
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
        List<ai.attus.gerenciamento_contratos.controllers.common.FieldError> listErrors = fieldErrors.stream().map(
                fieldError -> new ai.attus.gerenciamento_contratos.controllers.common.FieldError(fieldError.getField(), fieldError.getDefaultMessage())
        ).collect(Collectors.toList());
        System.out.println(e.getMessage());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error",
                listErrors);
    }


    @ExceptionHandler(DuplicateFieldValueException.class)
    public ResponseError handleValidationException(DuplicateFieldValueException ex) {
        List<ai.attus.gerenciamento_contratos.controllers.common.FieldError> fieldErrors = new ArrayList<ai.attus.gerenciamento_contratos.controllers.common.FieldError>();
        fieldErrors.add(ex.getField());
        return new ResponseError(HttpStatus.CONFLICT.value(), "Conflict error",
                fieldErrors);
    }



}
