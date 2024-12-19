package ai.attus.gerenciamento_contratos.exceptions;

public class InvalidContractStatusException extends RuntimeException {
    public InvalidContractStatusException(String message) {
        super(message);
    }
}
