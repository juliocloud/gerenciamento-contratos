package ai.attus.gerenciamento_contratos.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventServiceTest {
    @BeforeEach
    void setUp() {
        // Set up test data for Contract and Event
    }

    @Test
    @DisplayName("Should register event successfully")
    void shouldRegisterEventSuccessfully() {
        // Test registering an event successfully
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should throw exception when registering event for non-existent contract")
    void shouldThrowExceptionWhenRegisteringEventForNonExistentContract() {
        // Test throwing exception when registering an event for a non-existent contract
        // Mock repository behavior
        // Call service method and expect exception
    }

    @Test
    @DisplayName("Should throw exception when registering event with date older than the contract")
    void shouldThrowExceptionWhenRegisteringEventWithOlderDate() {
        // Test throwing exception when registering an event with older date than the contract
        // Mock repository behavior
        // Call service method and expect exception
    }

    @Test
    @DisplayName("Should throw exception when trying to register a signature of a terminated contract")
    void shouldThrowExceptionWhenSigningATerminatedContract() {
        // Test throwing exception when trying to sign a terminated contract
        // Mock repository behavior
        // Call service method and expect exception
    }



}
