package ai.attus.gerenciamento_contratos.controllers.common;

import org.springframework.http.HttpStatus;

public record GeneralError(String message, Integer status) {
}
