package ai.attus.gerenciamento_contratos.controllers;

import ai.attus.gerenciamento_contratos.models.Event;
import ai.attus.gerenciamento_contratos.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> registerEvent(@RequestBody @Valid Event event){
        var created = eventService.registerEvent(event);
        return ResponseEntity.ok(created);
    }
}
