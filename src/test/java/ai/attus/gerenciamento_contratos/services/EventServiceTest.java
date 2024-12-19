package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.enums.EventType;
import ai.attus.gerenciamento_contratos.exceptions.InvalidContractStatusException;
import ai.attus.gerenciamento_contratos.exceptions.InvalidPartySignatureException;
import ai.attus.gerenciamento_contratos.exceptions.ReferencedObjectDoesntExistException;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.models.Event;
import ai.attus.gerenciamento_contratos.models.Party;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import ai.attus.gerenciamento_contratos.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Service
@SpringBootTest
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private PartyService partyService;

    @Mock
    private ContractService contractService;

    private Event testEvent;
    private Contract testContract;
    private Party testParty;

    @BeforeEach
    void setUp() {
        testContract = new Contract();
        testContract.setNumber("CONT-001");
        testContract.setStatus(ContractStatus.ACTIVE);
        testContract.setNumber("CONT-001");
        testContract.setDescription("Sample Contract");

        testParty = new Party();
        testParty.setId("PARTY-001");
        testParty.setFullName("Test Party");

        testEvent = new Event();
        testEvent.setId("EVENT-001");
        testEvent.setContractId(testContract.getNumber());
        testEvent.setPartyId(testParty.getId());
        testEvent.setType(EventType.SIGNATURE);
        testEvent.setRegistrationDate(LocalDate.now());
    }

    @Test
    void shouldRegisterEventSuccessfully() {
        testContract.setStatus(null);

        testEvent.setContractId(testContract.getNumber());
        testEvent.setType(EventType.SIGNATURE);
        testEvent.setPartyId(testParty.getId());

        when(contractRepository.findById(testContract.getNumber())).thenReturn(Optional.of(testContract));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(partyService.getById(testParty.getId())).thenReturn(Optional.of(testParty));
        when(partyService.getPartiesAssociatedWithContract(testContract.getNumber())).thenReturn(Collections.singletonList(testParty));

        Event registeredEvent = eventService.registerEvent(testEvent);

        assertNotNull(registeredEvent);
        assertEquals(testEvent.getType(), registeredEvent.getType());
        verify(eventRepository).save(testEvent);
        verify(contractService).seal(ContractStatus.ACTIVE, testContract.getNumber());
    }



    @Test
    void shouldThrowInvalidContractStatusExceptionForInvalidStatus() {
        testContract.setStatus(null);

        testEvent.setContractId(testContract.getNumber());
        testEvent.setType(EventType.RENEWAL);
        testEvent.setPartyId(testParty.getId());

        when(contractRepository.findById(testContract.getNumber())).thenReturn(Optional.of(testContract));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        when(partyService.getById(testParty.getId())).thenReturn(Optional.of(testParty));
        when(partyService.getPartiesAssociatedWithContract(testContract.getNumber())).thenReturn(Collections.singletonList(testParty));

        assertThrows(InvalidContractStatusException.class, () -> {
            eventService.registerEvent(testEvent);
        });
    }

    @Test
    void shouldThrowInvalidContractStatusExceptionForSignEvents() {
        shouldThrowInvalidContractStatusException(ContractStatus.SUSPENDED, EventType.SIGNATURE);
    }

    @Test
    void shouldThrowInvalidContractStatusExceptionForTermination() {
        shouldThrowInvalidContractStatusException(null, EventType.TERMINATION); // Here, setting status to null
    }

    private void shouldThrowInvalidContractStatusException(ContractStatus status, EventType eventType) {
        testContract.setStatus(status);

        testEvent.setContractId(testContract.getNumber());
        testEvent.setType(eventType);
        testEvent.setPartyId(testParty.getId());

        when(contractRepository.findById(testContract.getNumber())).thenReturn(Optional.of(testContract));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        when(partyService.getById(testParty.getId())).thenReturn(Optional.of(testParty));
        when(partyService.getPartiesAssociatedWithContract(testContract.getNumber())).thenReturn(Collections.singletonList(testParty));

        assertThrows(InvalidContractStatusException.class, () -> {
            eventService.registerEvent(testEvent);
        });
    }

    @Test
    void shouldThrowInvalidPartySignatureExceptionWhenInvalidParty() {
        when(partyService.getById(testEvent.getPartyId())).thenReturn(Optional.of(testParty));
        when(partyService.getPartiesAssociatedWithContract(testEvent.getContractId())).thenReturn(Collections.emptyList());

        assertThrows(InvalidPartySignatureException.class, () -> eventService.validatePartySignature(testEvent));
    }

    @Test
    void shouldThrowReferencedObjectDoesntExistExceptionWhenPartyDoesNotExist() {
        when(partyService.getById(testEvent.getPartyId())).thenReturn(Optional.empty());

        assertThrows(ReferencedObjectDoesntExistException.class, () -> eventService.validatePartySignature(testEvent));
    }

    @Test
    void shouldHandlePostEventRegisteredForActivationEvents() {
        shouldHandlePostEventRegistered(EventType.SIGNATURE, ContractStatus.ACTIVE);
    }

    @Test
    void shouldHandlePostEventRegisteredForTerminationEvents() {
        shouldHandlePostEventRegistered(EventType.TERMINATION, ContractStatus.FINISHED);
    }

    private void shouldHandlePostEventRegistered(EventType eventType, ContractStatus expectedStatus) {
        testEvent.setType(eventType);

        List<Event> modifiableEvents = new ArrayList<>();
        modifiableEvents.add(testEvent);

        when(eventRepository.findByContractId(testEvent.getContractId())).thenReturn(modifiableEvents);
        when(partyService.getPartiesAssociatedWithContract(testEvent.getContractId())).thenReturn(Collections.singletonList(testParty));

        eventService.handlePostEventRegistered(testEvent);

        verify(contractService).seal(expectedStatus, testEvent.getContractId());
    }

}
