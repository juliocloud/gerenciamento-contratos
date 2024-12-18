package ai.attus.gerenciamento_contratos;

import ai.attus.gerenciamento_contratos.enums.ContractStatus;
import ai.attus.gerenciamento_contratos.models.Contract;
import ai.attus.gerenciamento_contratos.repository.ContractRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class GerenciamentoContratosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoContratosApplication.class, args);
	}

}
