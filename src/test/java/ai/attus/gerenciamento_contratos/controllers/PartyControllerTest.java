package ai.attus.gerenciamento_contratos.controllers;

import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.services.PartyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class PartyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PartyService partyService;

    @InjectMocks
    private PartyController partyController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(partyController).build();
    }


    @Test
    void shouldRegisterPartySuccessfully() throws Exception {
        Party party = new Party();
        party.setId("1");
        party.setContractId("12345");
        party.setFullName("Test Party");

        when(partyService.registerParty(any(Party.class))).thenReturn(party);

        mockMvc.perform(post("/api/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(party)))
                .andExpect(status().isOk()) // Expecting 200 OK
                .andExpect(jsonPath("$.fullName").value("Test Party"))
                .andExpect(jsonPath("$.contractId").value("12345"))
                .andExpect(jsonPath("$.id").value("1"));

        verify(partyService, times(1)).registerParty(any(Party.class));
    }

    @Test
    void shouldFailOnRegisterPartyInvalidInput() throws Exception {
        Party party = new Party();

        mockMvc.perform(post("/api/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(party)))
                .andExpect(status().isBadRequest()); // Expecting 400 for invalid input

        verify(partyService, times(0)).registerParty(any(Party.class));
    }


}
