package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.exceptions.ReferencedObjectDoesntExistException;
import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.repository.PartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PartyServiceTest {

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private ContractService contractService;

    @InjectMocks
    private PartyService partyService;

    private Party party;

    @BeforeEach
    void setUp() {
        party = new Party();
        party.setId("123");
        party.setContractId("C-001");
    }

    @Test
    void shouldThrowDuplicatePartyNumber() {
        when(partyRepository.existsById(party.getId())).thenReturn(true);

        MakeFieldError expectedError = new MakeFieldError("id", "Duplicate party number");

        DuplicateFieldValueException exception = assertThrows(DuplicateFieldValueException.class, () -> {
            partyService.registerParty(party);
        });

        assertEquals(expectedError.field(), exception.getField().field());
        assertEquals(expectedError.error(), exception.getField().error());
    }

    @Test
    void shouldThrowContractDoesntExist() {
        when(partyRepository.existsById(party.getId())).thenReturn(false);
        when(contractService.existsById(party.getContractId())).thenReturn(false);

        MakeFieldError expectedError = new MakeFieldError("contractId", "Contract doesn't exist in the database");

        ReferencedObjectDoesntExistException exception = assertThrows(ReferencedObjectDoesntExistException.class, () -> {
            partyService.registerParty(party);
        });

        assertEquals(expectedError.field(), exception.getField().field());
        assertEquals(expectedError.error(), exception.getField().error());
    }

    @Test
    void shouldRegisterPartySuccessfully() {
        when(partyRepository.existsById(party.getId())).thenReturn(false);
        when(contractService.existsById(party.getContractId())).thenReturn(true);
        when(partyRepository.save(any(Party.class))).thenReturn(party);

        Party result = partyService.registerParty(party);

        assertNotNull(result);
        assertEquals(party.getId(), result.getId());
        assertEquals(party.getContractId(), result.getContractId());

        verify(partyRepository, times(1)).save(party);
    }

    @Test
    void shouldGetPartiesAssociatedWithContract() {
        String contractNumber = "C-001";
        when(partyRepository.findByContractNumber(contractNumber)).thenReturn(List.of(party));

        var parties = partyService.getPartiesAssociatedWithContract(contractNumber);

        assertNotNull(parties);
        assertFalse(parties.isEmpty());
        assertEquals(1, parties.size());
    }

    @Test
    void shouldGetById() {
        when(partyRepository.findById("123")).thenReturn(Optional.of(party));

        Optional<Party> result = partyService.getById("123");

        assertTrue(result.isPresent());
        assertEquals(party.getId(), result.get().getId());
    }

    @Test
    void shouldAssertIdNotFound() {
        when(partyRepository.findById("123")).thenReturn(Optional.empty());

        Optional<Party> result = partyService.getById("123");

        assertFalse(result.isPresent());
    }
}
