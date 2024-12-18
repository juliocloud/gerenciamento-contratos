package ai.attus.gerenciamento_contratos.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceTest {
    @BeforeEach
    void setUp() {
        // Instantiate a new Contract
        // Instantiate a new Party
        // Instantiate a new Contact
        // Set properties
    }

    @Test
    @DisplayName("Should create contract successfully")
    void shouldCreateAContract() {
        // Test creating a contract successfully
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should edit contract successfully")
    void shouldEditContractSuccessfully() {
        // Test editing a contract successfully
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should add party to contract successfully")
    void shouldAddPartyToContractSuccessfully() {
        // Test adding a party to a contract successfully
        // Mock repository behavior
        // Call service method
        // Verify repository interactions
        // Assert results
    }

    @Test
    @DisplayName("Should update contract status successfully")
    void shouldUpdateContractStatusSuccessfully() {
        // Test updating the status of a contract successfully
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should verify if contract events are coherent with the current contract status")
    void shouldVerifyContractStatusAndEvents() {
        // Test if the order of events will affect the status
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should throw exception when creating contract with duplicate number")
    void shouldThrowExceptionWhenCreatingContractWithDuplicateNumber() {
        // Test throwing exception when creating a contract with a duplicate number
        // Mock repository behavior
        // Call service method and expect exception
    }

    @Test
    @DisplayName("Should throw exception when trying to modify a not-active contract")
    void shouldThrowExceptionWhenModifyingANonActiveContract() {
        // Test throwing exception when trying to modify an ended contract
        // Mock repository behavior
        // Call service method and expect exception
    }
}
