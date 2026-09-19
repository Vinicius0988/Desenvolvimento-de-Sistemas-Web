# Travel API — API REST para Gerenciamento de Destinos de Viagem

API RESTful desenvolvida em **Java 17** e **Spring Boot 3.3.4** para uma agência de viagens que está modernizando seus serviços digitais. Esta versão representa a **evolução da plataforma**, migrando a persistência temporária em memória para **PostgreSQL com Spring Data JPA** e adicionando **autenticação e controle de acesso com Spring Security**.

## 1. Visão Geral do Projeto

A **Travel API** serve como camada de integração para aplicativos de turismo, parceiros comerciais e plataformas digitais da agência.

Nesta versão **2.0.0**, a API evoluiu de uma prova de conceito para um sistema pronto para integração persistente, contando com:

* **Persistência Relacional:** Integração com PostgreSQL via Spring Data JPA.

* **Segurança e Perfis de Acesso:** Controle granular de endpoints por perfil (`ADMIN` e `USER`) usando Spring Security.

* **Criptografia de Senhas:** Armazenamento seguro de credenciais com algoritmo BCrypt.

* **Tratamento de Erros e Validação:** Respostas HTTP padronizadas e validações declarativas via DTOs.

## 2. Arquitetura e Organização em Camadas

O projeto segue uma **arquitetura em camadas (Layered Architecture)** bem definida, desacoplando requisições HTTP, regras de negócio e a camada de dados.

```
Cliente (App / Insomnia / Frontend)
         │  HTTP (JSON + Auth)
         ▼
┌─────────────────────────────────┐
│           Controller            │  → Recebe requisições HTTP, valida entradas, checa permissões
├─────────────────────────────────┤
│            Service              │  → Regras de negócio, transações, recálculo de médias e DTOs
├─────────────────────────────────┤
│           Repository            │  → Interfaces Spring Data JPA conectadas ao PostgreSQL
├─────────────────────────────────┤
│          Model / Entity         │  → Mapeamento ORM (Destino, Usuario, Role, Avaliacao)
└─────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────┐
│        Banco PostgreSQL         │  → Persistência de dados relacional
└─────────────────────────────────┘

```

### Estrutura do Código

```
travel-api/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/agencia/travelapi/
    │   │   ├── TravelApiApplication.java      # Classe principal da aplicação
    │   │   ├── config/                        # Configurações de Segurança e DataLoader
    │   │   ├── controller/                    # Endpoints REST (DestinoController, AuthController)
    │   │   ├── dto/                           # DTOs de entrada e saída (DestinoRequestDTO, etc.)
    │   │   ├── exception/                     # Exception Handler Global e exceções personalizadas
    │   │   ├── model/                         # Entidades JPA (Destino, Usuario, Role, Avaliacao)
    │   │   ├── repository/                    # Interfaces Spring Data JPA
    │   │   └── service/                       # Camada de serviços e regras de negócio
    │   └── resources/
    │       └── application.properties         # Configurações de conexão e JPA
    └── test/                                  # Testes unitários e de integração

```

## 3. Tecnologias Utilizadas

* **Linguagem:** Java 17

* **Framework Principal:** Spring Boot 3.3.4

* **Módulos Spring:**

  * **Spring Web:** Criação de endpoints RESTful.

  * **Spring Data JPA / Hibernate:** Mapeamento objeto-relacional e persistência.

  * **Spring Security:** Autenticação e autorização por perfis.

  * **Validation:** Validação declarativa de entrada de dados.

* **Banco de Dados:** PostgreSQL

* **Gerenciador de Dependências:** Maven

## 4. Banco de Dados e Configuração

### 1. Criar o Banco no PostgreSQL

Antes de iniciar a aplicação, crie o banco de dados no seu servidor PostgreSQL:

```
CREATE DATABASE travel_api;

```

### 2. Parâmetros do `application.properties`

O arquivo `src/main/resources/application.properties` vem configurado com suporte a variáveis de ambiente para facilitar a execução em diferentes ambientes (Desenvolvimento, Docker, Produção):

```
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/travel_api}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

### 3. Definindo Variáveis de Ambiente (Opcional)

Se precisar alterar o usuário, senha ou URL do banco sem editar o código fonte, defina no terminal:

* **Windows PowerShell:**

  ```
  $env:DB_URL="jdbc:postgresql://localhost:5432/travel_api"
  $env:DB_USERNAME="postgres"
  $env:DB_PASSWORD="sua_senha"
  
  ```

* **Linux / macOS:**

  ```
  export DB_URL=jdbc:postgresql://localhost:5432/travel_api
  export DB_USERNAME=postgres
  export DB_PASSWORD=sua_senha
  
  ```

## 5. Autenticação e Perfis de Acesso

A API utiliza a estratégia **HTTP Basic** para autenticação. Na inicialização do sistema, a classe `DataLoader` cria automaticamente dois usuários de teste para validar os perfis de acesso:

| 

| **Usuário** | **Senha** | **Perfil** | **Permissões** | 
| `admin` | `admin123` | `ADMIN` | Acesso total (Criar, Editar, Listar, Excluir, Avaliar) | 
| `usuario` | `user123` | `USER` | Leitura (Listar) e Avaliação de destinos | 
| *Anônimo* | *Nenhum* | *Público* | Apenas leitura e consulta aos destinos | 

*Nota: As senhas são armazenadas com hash **BCrypt** no banco de dados.*

## 6. Endpoints da API e Regras de Segurança

**Base URL:** `http://localhost:8080`

| **Método** | **Endpoint** | **Descrição** | **Permissão Requerida** | 
| `GET` | `/api/destinos` | Lista todos os destinos (suporta filtros `?nome=` e `?localizacao=`) | **Público** | 
| `GET` | `/api/destinos/{id}` | Busca os detalhes de um destino específico | **Público** | 
| `POST` | `/api/destinos` | Cadastra um novo destino | `ADMIN` | 
| `PUT` | `/api/destinos/{id}` | Atualiza os dados de um destino existente | `ADMIN` | 
| `DELETE` | `/api/destinos/{id}` | Exclui um destino | `ADMIN` | 
| `PATCH` | `/api/destinos/{id}/avaliacoes` | Registra nota (0 a 5) e recalcula a média | `USER` ou `ADMIN` | 
| `GET` | `/api/auth/me` | Retorna as informações do usuário autenticado | **Autenticado** (`USER` ou `ADMIN`) | 

## 7. Exemplos de Uso (curl)

### Consultar destinos (Público)

```
curl http://localhost:8080/api/destinos

```

### Checar dados do perfil logado

```
curl -u usuario:user123 http://localhost:8080/api/auth/me

```

### Cadastrar novo destino (Requer ADMIN)

```
curl -u admin:admin123 -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Gramado",
    "localizacao": "Rio Grande do Sul, Brasil",
    "descricao": "Destino turístico na Serra Gaúcha",
    "categoria": "Nacional",
    "disponivel": true,
    "precoBase": 1200.00
  }'

```

*(Se tentar executar este comando usando a credencial `-u usuario:user123`, a API retornará `403 Forbidden`).*

### Registrar avaliação (Requer USER ou ADMIN)

```
curl -u usuario:user123 -X PATCH http://localhost:8080/api/destinos/1/avaliacoes \
  -H "Content-Type: application/json" \
  -d '{ "nota": 5 }'

```

### Excluir um destino (Requer ADMIN)

```
curl -u admin:admin123 -X DELETE http://localhost:8080/api/destinos/1

```

## 8. Como Executar o Projeto

### Pré-requisitos

* Java 17 instalado e configurado nas variáveis de ambiente (`JAVA_HOME`).

* Banco PostgreSQL rodando localmente ou via container Docker.

* Maven 3.8+ (ou usar o wrapper `mvnw` do repositório).

### Passo a passo

1. **Clonar o repositório:**

   ```
   git clone <URL_DO_SEU_REPOSITORIO>
   cd travel-api
   
   ```

2. **Garantir que o PostgreSQL está rodando** e que o banco `travel_api` foi criado.

3. **Executar a aplicação:**

   ```
   mvn spring-boot:run
   
   ```

   *(Ou compilar o arquivo JAR e executar diretamente)*:

   ```
   mvn clean package
   java -jar target/travel-api-2.0.0.jar
   
   ```

4. **Rodar a suíte de testes:**

   ```
   mvn test
   
   ```

## 9. Versionamento e Boas Práticas (Git)

Para salvar e enviar a evolução da sua aplicação para o repositório remoto:

```
git init
git add .
git commit -m "Evolui API com PostgreSQL, JPA e Spring Security"
git branch -M main
git remote add origin SEU_REPOSITORIO
git push -u origin main

```

> **Importante:** Nunca publique senhas reais do banco de dados no seu repositório Git. Utilize as variáveis de ambiente (`DB_USERNAME` e `DB_PASSWORD`).

## 10. Próximos Passos e Evolução Futura

* **Autenticação com JWT:** Migração do HTTP Basic para tokens Bearer JWT para suporte stateless em clientes web/mobile.

* **Documentação OpenAPI/Swagger:** Adição do módulo `springdoc-openapi-starter-webmvc-ui` para geração automática da interface de testes Swagger UI.

* **Paginação:** Implementar paginação e ordenação (`Pageable`) nos endpoints de listagem de destinos.

* **Conteinerização:** Criação de um arquivo `docker-compose.yml` para subir a aplicação e o banco PostgreSQL em containers de forma automatizada.