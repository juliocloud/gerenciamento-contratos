package ai.attus.gerenciamento_contratos.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
public class ContractControllerTest {
    @Test
    @DisplayName("Should create contract via API successfully")
    void shouldCreateContractViaAPISuccessfully() throws Exception {
        // Test creating a contract via API successfully
        // Mock service behavior
        // Perform POST request to create contract
        // Assert response status and content
    }

    @Test
    @DisplayName("Should return error when creating invalid contract")
    void shouldReturnErrorWhenCreatingInvalidContract() throws Exception {
        // Test returning error when creating an invalid contract
        // Perform POST request with invalid contract data
        // Assert response status
    }

}
