package ai.attus.gerenciamento_contratos.model;

import java.time.LocalDateTime;
import java.util.List;

public class Contract {
    private String number;
    private LocalDateTime creationDate;
    private String description;
    private List<Party> parties;
    private List<Event> events;
}
