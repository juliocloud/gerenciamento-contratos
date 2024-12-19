package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Service
@SpringBootTest
class ContractServiceTest {

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
        testContract.setDescription("Contract de Teste");
        testContract.setCreationDate(LocalDate.now());
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
    @DisplayName("Should update contract status successfully")
    void shouldUpdateContractStatusSuccessfully() {
        when(contractRepository.findById(anyString())).thenReturn(Optional.of(testContract));
        when(contractRepository.save(any(Contract.class))).thenReturn(testContract);

        Contract edited = new Contract();
        edited.setNumber("CONT-001");
        edited.setDescription("This description is now edited");
        edited.setCreationDate(LocalDate.now());
        edited.setStatus(ContractStatus.ACTIVE);

        contractService.updateContract("CONT-001", edited);

        assertEquals(edited.getDescription(), testContract.getDescription());
        verify(contractRepository).save(testContract);

    }

    @Test
    @DisplayName("Should archive a contract successfully")
    void shoudArchiveContractSuccessfully() {
        when(contractRepository.findById(anyString())).thenReturn(Optional.of(testContract));
        when(contractRepository.save(any(Contract.class))).thenReturn(testContract);

        contractService.archiveContract("CONT-001");

        assertEquals(ContractStatus.SUSPENDED, testContract.getStatus());
        verify(contractRepository).save(testContract);
    }


    @Test
    @DisplayName("Should search a contract by status successfully")
    void shouldSearchContractByStatusSuccessfully() {
        when(contractRepository.findByStatus(ContractStatus.ACTIVE))
                .thenReturn(List.of(testContract));

        List<Contract> result = contractService.searchByStatus(ContractStatus.ACTIVE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(testContract.getNumber(), result.getFirst().getNumber());
        verify(contractRepository).findByStatus(ContractStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should search contracts by creation date successfully")
    void shouldSearchContractByCreationDateSuccessfully() {
        when(contractRepository.findByCreationDate(testContract.getCreationDate()))
                .thenReturn(List.of(testContract));

        List<Contract> result = contractService.searchByCreationDate(testContract.getCreationDate());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(testContract.getNumber(), result.getFirst().getNumber());
        verify(contractRepository).findByCreationDate(testContract.getCreationDate());
    }


    @Test
    @DisplayName("Should search a contract by identification successfully")
    void shouldSearchContractByIdentificationSuccessfully() {
        when(contractRepository.findById("CONT-001"))
                .thenReturn(Optional.of(testContract));

        Optional<Contract> result = contractService.findById("CONT-001");

        assertTrue(result.isPresent());
        assertEquals(testContract.getNumber(), result.get().getNumber());
        verify(contractRepository).findById("CONT-001");
    }

    @Test
    @DisplayName("Should throw exception when creating contract with duplicate number")
    void shouldThrowExceptionWhenCreatingContractWithDuplicateNumber() {
        when(contractRepository.existsById(anyString())).thenReturn(true);

        assertThrows(DuplicateFieldValueException.class, () -> {
            contractService.createContract(testContract);
        });
    }

}
