package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.controllers.common.MakeFieldError;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final ContractRepository contractRepository;

    private final PartyService partyService;

    private final ContractService contractService;


    @Autowired
    public EventService(EventRepository eventRepository, ContractRepository contractRepository, PartyService partyService, ContractService contractService) {
        this.eventRepository = eventRepository;
        this.contractRepository = contractRepository;
        this.partyService = partyService;
        this.contractService = contractService;
    }

    @Transactional
    public Event registerEvent(Event event){
        fillEvent(event);
        validations(event);
        Event registered = eventRepository.save(event);
        handlePostEventRegistered(event);
        return registered;
    }

    private void fillEvent(Event event){
        String id = UUID.randomUUID().toString();
        LocalDate currentDate = LocalDate.now();

        event.setId(id);
        event.setRegistrationDate(currentDate);
    }

    private void validateSignatureType(Event event) {
        Contract contract = contractRepository.findById(event.getContractId())
                .orElseThrow(() -> new IllegalArgumentException("Contract not found for ID: " + event.getContractId()));

        ContractStatus contractStatus = contract.getStatus();
        EventType registerType = event.getType();

        switch (registerType) {
            case EventType.SIGNATURE:
                if (contractStatus != null) {
                    MakeFieldError fieldError = new MakeFieldError("status","Contract status must be null for SIGN events.");
                    throw new InvalidContractStatusException(fieldError);
                }
                break;
            case EventType.RENEWAL, EventType.TERMINATION:
                if (!ContractStatus.ACTIVE.equals(contractStatus) && !ContractStatus.SUSPENDED.equals(contractStatus)) {
                    MakeFieldError fieldError = new MakeFieldError("status","Contract status must be ACTIVE or SUSPENDED for event." + registerType);
                    throw new InvalidContractStatusException(fieldError);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid register type: " + registerType);
        }
    }

    public void validatePartySignature(Event event) {
        List<Party> associatedParties = partyService.getPartiesAssociatedWithContract(event.getContractId());

        Optional<Party> referencedEventParty = partyService.getById(event.getPartyId());

        if (referencedEventParty.isEmpty()) {
            MakeFieldError fieldError = new MakeFieldError("partyId", "Party not found");
            throw new ReferencedObjectDoesntExistException(fieldError);
        }

        if (!associatedParties.contains(referencedEventParty.get())) {
            MakeFieldError fieldError = new MakeFieldError("partyId", "Invalid party signature");
            throw new InvalidPartySignatureException(fieldError);
        }
    }

    public void validations(Event event){
        validateSignatureType(event);
        validatePartySignature(event);
    }

    public void handlePostEventRegistered(Event event) {
        List<Event> events = eventRepository.findByContractId(event.getContractId());
        List<Party> parties = partyService.getPartiesAssociatedWithContract(event.getContractId());

        events.add(event);

        boolean allSameType = events.stream()
                .map(Event::getType)
                .distinct()
                .count() == 1;

        if (!allSameType) {
            return;
        }

        boolean allPartiesSigned = parties.stream()
                .allMatch(party ->
                        events.stream()
                                .anyMatch(eventItem ->
                                        eventItem.getPartyId().equals(party.getId())
                                )
                );

        if (!allPartiesSigned) {
            return;
        }

        switch (event.getType()){
            case RENEWAL, SIGNATURE:
                contractService.seal(ContractStatus.ACTIVE, event.getContractId());
                break;
            case TERMINATION:
                contractService.seal(ContractStatus.FINISHED, event.getContractId());
                break;
            default:
                contractService.seal(ContractStatus.SUSPENDED, event.getContractId());
        }
    }

}
