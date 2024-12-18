package ai.attus.gerenciamento_contratos.models;

import ai.attus.gerenciamento_contratos.enums.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

//@Entity
public class Event {

    //@Id
    private String id;
    private EventType type;
    private LocalDateTime registrationDate;
    private String description;
}
