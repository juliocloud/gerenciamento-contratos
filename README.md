# Gerenciamento de contratos

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

