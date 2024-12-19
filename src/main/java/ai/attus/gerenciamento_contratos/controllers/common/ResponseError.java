package ai.attus.gerenciamento_contratos.controllers.common;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ResponseError(int status, String message, List<MakeFieldError> errors) {
    public static ResponseError standardResponse(String message){
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }
}
