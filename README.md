# Projeto Gestão de Fila de Espera do SEAC com Docker

Este repositório contém o projeto Java Spring Boot `gestao_fila_api`, responsável pela gestão de filas de espera do SEAC. O sistema permite visualizar a posição na fila em tempo real utilizando Apache Kafka, além de registrar logs diretamente na base de dados `p12_db`. O Docker Compose é utilizado para gerenciar os serviços.

## Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:
- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Configuração do Projeto

### 1. Clonar o Repositório
```sh
git clone https://github.com/ZeBriguento/gestao_fila_api.git
cd gestao_fila_api
```

### 2. Construir o Projeto

Antes de rodar a aplicação no Docker, é necessário compilar o projeto e gerar o arquivo `.jar`.
```sh
mvn clean package
```

### 3. Executar a Aplicação com Docker

Se o arquivo `docker-compose.yml` já existir no projeto, utilize o comando abaixo para construir e iniciar os contêineres:
```sh
docker-compose up --build -d
```

### 4. Verificar os Contêineres em Execução
```sh
docker ps
```

### 5. Acessar os Logs da Aplicação
```sh
docker logs -f nome-do-container
```

### 6. Parar e Remover os Contêineres
```sh
docker-compose down
```

## Funcionalidades Implementadas

O sistema `gestao_fila_api` inclui as seguintes funcionalidades:
- Gestão de usuários
- Gestão de filas
- Gestão de serviços
- Gestão de localidades
- Gestão de guichês
- Registro de logs na base de dados `p12_db`
- Gestão de pessoas
- Gestão de sexo
- Gestão de tipos de usuários
- Gestão de senhas

### Funcionalidades Principais
- Verificar posição na fila
- Chamar senha
- Cancelar senha
- Concluir senha
- Consultar senhas pendentes
- Visualização em tempo real via Kafka no endpoint `/stream`

## Acessar a Aplicação

Após iniciar os contêineres, acesse a aplicação pelo navegador ou via `curl`:
```sh
http://localhost:8080
```

## Configuração do Banco de Dados

O sistema utiliza a base de dados `p12_db`. Para inserir dados automaticamente no banco de dados, ative a seguinte configuração no `application.properties`:
```properties
spring.sql.init.mode=always
```

## Considerações Finais

Este projeto pode ser expandido para incluir autenticação com Spring Security, melhorias de performance e novas funcionalidades de gestão de filas.

---

🚀 Desenvolvido por [ZeBriguento]
