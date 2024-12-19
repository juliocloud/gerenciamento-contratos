package ai.attus.gerenciamento_contratos.controllers;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.services.ContractService;
import ai.attus.gerenciamento_contratos.models.Event;
import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.enums.EventType;
import ai.attus.gerenciamento_contratos.enums.PartyType;
import ai.attus.gerenciamento_contratos.enums.IdentificationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ContractControllerTest {

    @Mock
    private ContractService contractService;

    @InjectMocks
    private ContractController contractController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(contractController).build();
    }

    @Test
    void testCreateContract() throws Exception {
        Contract contract = new Contract(
                LocalDate.now(),
                "Sample Description",
                ContractStatus.ACTIVE
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(contract);

        mockMvc.perform(post("/api/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(contractService).createContract(any(Contract.class));
    }

    @Test
    void testUpdateContract() throws Exception {
        Contract updatedContract = new Contract(
                LocalDate.now(),
                "Updated Description",
                ContractStatus.ACTIVE
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updatedContract);

        mockMvc.perform(put("/api/contracts/sample-number")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(contractService).updateContract(anyString(), any(Contract.class));
    }

    @Test
    void testArchiveContract() throws Exception {
        mockMvc.perform(put("/api/contracts/archive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contractNumber\":\"sample-number\"}"))  // Send contract number in the body
                .andExpect(status().isNoContent());

        verify(contractService).archiveContract("sample-number");
    }

    @Test
    void testSearchByStatus() throws Exception {
        Contract contract = new Contract(
                LocalDate.now(),
                "Sample Description",
                ContractStatus.ACTIVE
        );

        List<Contract> contracts = Arrays.asList(contract);

        when(contractService.searchByStatus(any(ContractStatus.class))).thenReturn(contracts);

        mockMvc.perform(get("/api/contracts/find/status/ACTIVE"))
                .andExpect(status().isOk());

        verify(contractService).searchByStatus(ContractStatus.ACTIVE);
    }

    @Test
    void testSearchByCreationDate() throws Exception {
        Contract contract = new Contract(
                LocalDate.of(2023, 1, 1),
                "Sample Description",
                ContractStatus.ACTIVE
        );

        List<Contract> contracts = Arrays.asList(contract);

        when(contractService.searchByCreationDate(any(LocalDate.class))).thenReturn(contracts);

        mockMvc.perform(get("/api/contracts/find/creationDate/2023-01-01"))
                .andExpect(status().isOk());

        verify(contractService).searchByCreationDate(LocalDate.of(2023, 1, 1));
    }

    @Test
    void testSearchByIdentification() throws Exception {
        Party party = new Party(
                "sample-id",
                "12345678900",
                "Joao ATTUS",
                IdentificationType.CPF,
                PartyType.CLIENT,
                "joao@attus.ai",
                "123456789"
        );

        Contract contract = new Contract(
                LocalDate.now(),
                "Sample Description",
                ContractStatus.ACTIVE
        );
        contract.setParties(Arrays.asList(party));

        when(contractService.searchByIdentification("12345678900")).thenReturn(Arrays.asList(contract));

        mockMvc.perform(get("/api/contracts/find/partyId/12345678900"))
                .andExpect(status().isOk());

        verify(contractService).searchByIdentification("12345678900");
    }
}
