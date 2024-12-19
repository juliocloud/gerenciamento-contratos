package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.GlobalExceptionHandler;
import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;
import ai.attus.gerenciamento_contratos.controllers.common.ResponseError;
import ai.attus.gerenciamento_contratos.exceptions.InvalidContractStatusException;
import ai.attus.gerenciamento_contratos.exceptions.InvalidPartySignatureException;
import ai.attus.gerenciamento_contratos.exceptions.ReferencedObjectDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @Test
    void testHandleInvalidContractStatus() {
        // Arrange
        MakeFieldError fieldError = new MakeFieldError("contractStatus", "Contract status is invalid.");
        InvalidContractStatusException exception = new InvalidContractStatusException(fieldError);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Act
        ResponseError result = handler.handleInvalidContractStatus(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.status());
        assertEquals("Invalid value", result.message());
        assertEquals(1, result.errors().size());
        assertEquals(fieldError, result.errors().get(0));  // Verifying fieldError is included in errors list
    }

    @Test
    void testHandleReferencedNonExistingObject() {
        // Arrange
        MakeFieldError fieldError = new MakeFieldError("referencedObject", "Referenced object does not exist.");
        ReferencedObjectDoesntExistException exception = new ReferencedObjectDoesntExistException(fieldError);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseError result = handler.handleReferencedNonExistingObject(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.status());
        assertEquals("Referencing non existing object", result.message());
        assertEquals(1, result.errors().size());
        assertEquals(fieldError, result.errors().get(0));  // Verifying fieldError is included in errors list
    }

    @Test
    void testHandleInvalidPartySignature() {
        // Arrange
        MakeFieldError fieldError = new MakeFieldError("partySignature", "Party signature is invalid.");
        InvalidPartySignatureException exception = new InvalidPartySignatureException(fieldError);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Act
        ResponseError result = handler.handleInvalidPartySignature(exception);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.status());
        assertEquals("Invalid party signature", result.message());
        assertEquals(1, result.errors().size());
        assertEquals(fieldError, result.errors().get(0));  // Verifying fieldError is included in errors list
    }
}
