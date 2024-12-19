package ai.attus.gerenciamento_contratos.controllers.common;

import java.io.Serializable;

public record MakeFieldError(String field, String error) implements Serializable {
}
