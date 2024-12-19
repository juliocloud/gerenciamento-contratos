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
        // Initialize MockMvc and ObjectMapper
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(partyController).build();
    }


    @Test
    void testRegisterParty_Success() throws Exception {
        // Given: Creating a valid Party object with all required fields
        Party party = new Party();
        party.setId("1"); // Providing required fields
        party.setContractId("12345");
        party.setFullName("Test Party");

        // Mock service response
        when(partyService.registerParty(any(Party.class))).thenReturn(party);

        // When & Then
        mockMvc.perform(post("/api/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(party)))
                .andExpect(status().isOk()) // Expecting 200 OK
                .andExpect(jsonPath("$.fullName").value("Test Party"))
                .andExpect(jsonPath("$.contractId").value("12345"))
                .andExpect(jsonPath("$.id").value("1"));

        // Verify service method was called
        verify(partyService, times(1)).registerParty(any(Party.class));
    }

    @Test
    void testRegisterParty_Failure_InvalidInput() throws Exception {
        // Given an invalid Party object (e.g., missing required fields)
        Party party = new Party(); // assuming fields are required and empty

        // When & Then
        mockMvc.perform(post("/api/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(party)))
                .andExpect(status().isBadRequest()); // Expecting 400 for invalid input

        // Verify service method was not called
        verify(partyService, times(0)).registerParty(any(Party.class));
    }


}
