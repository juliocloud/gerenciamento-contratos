# Gerenciamento de contratos

## Implementações não especificadas
Esta sessão tem como objetivo descrever decisões de arquitetura tomadas que diferem da proposta realizada. São pequenas implementações que, apesar de não especificadas, ajudaram no processo de desenvolvimento e nas implementações de regras de negócio

1. Na classe `Event`, localizada em `src/main/java/ai/attus/gerenciamento_contratos/models/Event.java`, foi adicionada uma propriedade `partyId`. Isso foi realizado para controle de quais partes assinaram um documento, e assim realizar o controle de assinaturas duplicadas
## Documentação
#### Diagrama de classes
Este diagrama descreve todas as classes utilizadas no sistema. Dentre as classes POJO do sistema, também foram destacadas as classes Service e Controllers
![Diagrama de classes](src/main/resources/docs/png_docs/classes.png)
#### Enums utilizados
Este diagrama descreve os enums utilizados no sistema, para descrever valores comuns utilizados no sistema
![Diagrama de classes enum](src/main/resources/docs/png_docs/enums.png)
#### Diagrama de estados
Este diagrama descreve os estados possíveis para os eventos. Cada evento está mapeado e descrito como Assinatura, Rescisão e Renovação
![Diagrama de estado](src/main/resources/docs/png_docs/state_diagram.png)

## Análise de qualidade
Para garantir a qualidade do código, foi utilizado o SonarQube. Para utilizar o Sonar, primeiro é necessário baixar e iniciar a imagem Docker com o seguinte comando:
```
$ docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

Depois de realizadas todas as configurações dentro do Sonar, rodando em `localhost:9000`, é necessário rodar o seguinte comando:
``` 
mvn clean verify sonar:sonar 
  -Dsonar.projectKey=nome_projeto 
  -Dsonar.projectName='nome_projeto' 
  -Dsonar.host.url=http://localhost:9000 
  -Dsonar.token=seu_token_gerado_no_sonar
```

## PostgreSQL
### Configuração
Foi utilizada a imagem Docker do postgres para utilização de banco de dados. Para subir a imagem docker do Postgres, é necessário utilizar o seguinte comando:
```
   docker run --name gerenciamento-contratos -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=attus postgres:16.3
```
Para a criação de tabelas é necessário acessar a instância docker com o comando `docker exec -it gerenciamento-contratos psql -U postgres -d attus`. Foi utilizado o seguinte esquema de tabelas:
```
CREATE TABLE contracts (
        contract_number VARCHAR(50) UNIQUE NOT NULL PRIMARY KEY,
        creation_date TIMESTAMP,
        description TEXT,
        status VARCHAR(20)
        );

        CREATE TABLE parties (
        id VARCHAR(200) PRIMARY KEY,
        contract_id VARCHAR(50) REFERENCES contracts(contract_number),
        full_name VARCHAR(100),
        document VARCHAR(20),
        type VARCHAR(20),
        email VARCHAR(100),
        phone VARCHAR(20)
        );

        CREATE TABLE events (
        id VARCHAR(200) PRIMARY KEY,
        contract_id VARCHAR(50) REFERENCES contracts(contract_number),
        event_type VARCHAR(20),
        event_date TIMESTAMP,
        description TEXT,
        party_id VARCHAR(200)
        );

```

