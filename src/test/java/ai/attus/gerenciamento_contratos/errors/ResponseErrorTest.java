package ai.attus.gerenciamento_contratos.errors;

import ai.attus.gerenciamento_contratos.controllers.common.ResponseError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResponseErrorTest {

    @Test
    void testStandardResponse() {
        ResponseError error = ResponseError.standardResponse("Test message");
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.status());
        assertEquals("Test message", error.message());
        assertTrue(error.errors().isEmpty());
    }
}
