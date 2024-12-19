package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.exceptions.ReferencedObjectDoesntExistException;
import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.repository.PartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PartyServiceTest {

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private ContractService contractService;

    @InjectMocks
    private PartyService partyService;

    private Party party;
    private String contractNumber = "12345";
    private String partyId = "party1";
    private String contractId = "contract1";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        party = new Party();
        party.setId(partyId);
        party.setContractId(contractId);
    }

    @Test
    public void shouldRegisterPartySuccessfully() {
        when(partyRepository.existsById(partyId)).thenReturn(false);
        when(contractService.existsById(contractId)).thenReturn(true);
        when(partyRepository.save(party)).thenReturn(party);

        Party registeredParty = partyService.registerParty(party);

        assertNotNull(registeredParty);
        assertEquals(partyId, registeredParty.getId());
        assertEquals(contractId, registeredParty.getContractId());
        verify(partyRepository, times(1)).save(party);
    }

    @Test
    public void shouldThrowDuplicatePartyIdException() {
        when(partyRepository.existsById(partyId)).thenReturn(true);

        MakeFieldError expectedError = new MakeFieldError("id", "Duplicate party number");

        DuplicateFieldValueException exception = assertThrows(DuplicateFieldValueException.class, () -> {
            partyService.registerParty(party);
        });

        assertNotNull(exception.getField());
        assertEquals(expectedError.field(), exception.getField().field());
        assertEquals(expectedError.error(), exception.getMessage());
        verify(partyRepository, never()).save(any(Party.class));
    }


    @Test
    public void shouldThrowReferencedObjectDoesntExistExceptionForNonExistingContract() {
        when(partyRepository.existsById(partyId)).thenReturn(false);
        when(contractService.existsById(contractId)).thenReturn(false);

        MakeFieldError expectedError = new MakeFieldError("contractId", "Contract doesn't exist in the database");

        ReferencedObjectDoesntExistException exception = assertThrows(ReferencedObjectDoesntExistException.class, () -> {
            partyService.registerParty(party);
        });

        assertNotNull(exception.getField());
        assertEquals(expectedError.field(), exception.getField().field());
        assertEquals(expectedError.error(), exception.getMessage());
        verify(partyRepository, never()).save(any(Party.class));
    }


    @Test
    public void shouldReturnPartiesAssociatedWithContract() {
        when(partyRepository.findByContractNumber(contractNumber)).thenReturn(List.of(party));

        var parties = partyService.getPartiesAssociatedWithContract(contractNumber);

        assertNotNull(parties);
        assertFalse(parties.isEmpty());
        assertEquals(1, parties.size());
        assertEquals(partyId, parties.getFirst().getId());
    }

    @Test
    public void shouldReturnEmptyIfPartyNotFoundById() {
        when(partyRepository.findById(partyId)).thenReturn(Optional.empty());

        Optional<Party> foundParty = partyService.getById(partyId);

        assertTrue(foundParty.isEmpty());
    }

    @Test
    public void shouldReturnPartyIfFoundById() {
        when(partyRepository.findById(partyId)).thenReturn(Optional.of(party));

        Optional<Party> foundParty = partyService.getById(partyId);

        assertTrue(foundParty.isPresent());
        assertEquals(partyId, foundParty.get().getId());
    }
}
