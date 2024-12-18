package ai.attus.gerenciamento_contratos.model;

import ai.attus.gerenciamento_contratos.enums.EventType;

import java.time.LocalDateTime;

public class Event {
    private String id;
    private EventType type;
    private LocalDateTime registrationDate;
    private String description;
}
