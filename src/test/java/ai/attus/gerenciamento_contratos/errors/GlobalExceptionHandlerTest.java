package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.GlobalExceptionHandler;
import ai.attus.gerenciamento_contratos.controllers.common.ResponseError;
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
    void testHandleMethodArgumentNotValidException() throws Exception {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        List<FieldError> fieldErrors = Arrays.asList(mock(FieldError.class));
        when(exception.getFieldErrors()).thenReturn(fieldErrors);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        Object result = handler.handleMethodArgumentNotValidException(exception);

        verify(exception).getFieldErrors();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), ((ResponseError)result).status());
        assertEquals("Validation error", ((ResponseError)result).message());
        assertEquals(1, ((ResponseError)result).errors().size());
    }

}
