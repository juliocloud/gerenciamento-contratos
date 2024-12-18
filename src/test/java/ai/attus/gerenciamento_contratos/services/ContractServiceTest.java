package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.models.Contact;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Service
@SpringBootTest
public class ContractServiceTest {

    @Autowired
    private ContractService contractService;

    @MockitoBean
    private ContractRepository contractRepository;

    private Contract testContract;

    private Party testParty;

    @BeforeEach
    void setUp() {
        testContract = new Contract();
        testContract.setNumber("CONT-001");
        testContract.setDescription("Contrato de Teste");
        testContract.setCreationDate(LocalDateTime.now());
        testContract.setStatus(ContractStatus.ACTIVE);
    }


    @Test
    @DisplayName("Should create contract successfully")
    void shouldCreateAContractSuccessfully() {
        when(contractRepository.existsById(anyString())).thenReturn(false);
        when(contractRepository.save(any(Contract.class))).thenReturn(testContract);

        Contract resultado = contractService.createContract(testContract);

        assertNotNull(resultado);
        assertEquals(testContract.getNumber(), resultado.getNumber());
        verify(contractRepository).save(testContract);
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
    @DisplayName("Should search a contract by status successfully")
    void shouldSearchContractByStatusSuccessfully() {
        // Test if it can search contract by status successfully
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should search a contract by creation date successfully")
    void shouldSearchContractByCreationDateSuccessfully() {
        // Test if it can search contract by creation date successfully
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should search a contract by identification successfully")
    void shouldSearchContractByIdentificationSuccessfully() {
        // Test if it can search contract by identification successfully
        // Mock repository behavior
        // Call service method
        // Assert results
        // Verify repository interactions
    }

    @Test
    @DisplayName("Should throw exception when creating contract with duplicate number")
    void shouldThrowExceptionWhenCreatingContractWithDuplicateNumber() {
        when(contractRepository.existsById(anyString())).thenReturn(true);

        assertThrows(DuplicateFieldValueException.class, () -> {
            contractService.createContract(testContract);
        });
    }

    @Test
    @DisplayName("Should throw exception when trying to modify a not-active contract")
    void shouldThrowExceptionWhenModifyingANonActiveContract() {
        // Test throwing exception when trying to modify an ended contract
        // Mock repository behavior
        // Call service method and expect exception
    }

    @Test
    @DisplayName("Should throw exception when trying to register an invalid party identification (CPF/CNPJ validation)")
    void shouldThrowExceptionWhenRegisteringInvalidIdentification() {
        // Test throwing exception when trying to register an invalid party identification
        // Mock repository behavior
        // Call service method and expect exception
    }
}
