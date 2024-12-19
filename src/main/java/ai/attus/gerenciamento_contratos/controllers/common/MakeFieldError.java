package ai.attus.gerenciamento_contratos.controllers.common;

public record MakeFieldError(String field, String error) {
    @Override
    public String field() {
        return field;
    }

    @Override
    public String error() {
        return error;
    }
}
