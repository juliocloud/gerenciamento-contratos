package ai.attus.gerenciamento_contratos.errors;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MakeFieldErrorTest {

    @Test
    void testMakeFieldError() {
        MakeFieldError error = new MakeFieldError("field", "error message");
        assertEquals("field", error.field());
        assertEquals("error message", error.error());
    }
}
