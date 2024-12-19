package ai.attus.gerenciamento_contratos.services;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.enums.EventType;
import ai.attus.gerenciamento_contratos.exceptions.DuplicateFieldValueException;
import ai.attus.gerenciamento_contratos.exceptions.InvalidContractStatusException;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.models.Event;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import ai.attus.gerenciamento_contratos.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final ContractRepository contractRepository;

    public EventService(EventRepository eventRepository, ContractRepository contractRepository) {
        this.eventRepository = eventRepository;
        this.contractRepository = contractRepository;
    }


    @Transactional
    public Event registerEvent(Event event){
        event = fillEvent(event);
        validateEvent(event);
        Event registered = eventRepository.save(event);
        // handlePostEventRegistered(event);
        return registered;
    }

    private Event fillEvent(Event event){
        String id = UUID.randomUUID().toString();
        LocalDate currentDate = LocalDate.now();

        event.setId(id);
        event.setRegistrationDate(currentDate);

        return event;
    }
    private void validateEvent(Event event) {
        Contract contract = contractRepository.findById(event.getContractId())
                .orElseThrow(() -> new IllegalArgumentException("Contract not found for ID: " + event.getContractId()));

        ContractStatus contractStatus = contract.getStatus();
        EventType registerType = event.getType();

        switch (registerType) {
            case EventType.SIGNATURE:
                if (contractStatus != null) {
                    throw new InvalidContractStatusException("Contract status must be null for SIGN events.");
                }
                break;
            case EventType.RENEWAL:
            case EventType.TERMINATION:
                if (!ContractStatus.ACTIVE.equals(contractStatus) && !ContractStatus.SUSPENDED.equals(contractStatus)) {
                    throw new InvalidContractStatusException(
                            String.format("Contract status must be ACTIVE or SUSPENDED for %s events.", registerType));
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid register type: " + registerType);
        }
    }


    //public void handlePostEventRegistered(Event event){
    //    List events = eventRepository.findByContractId(event.getContractId());
    //}
}
