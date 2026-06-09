# AgroSat — API Java (Spring Boot 3)

API REST da disciplina **Java Advanced** (Global Solution FIAP 2026/1).
**AgroSat** é agricultura de precisão: cruza dados de satélite (NDVI, umidade estimada, chuva)
com sensores ESP32 no campo (umidade do solo, temperatura) e aciona irrigação/alertas. ODS 2, 8, 9, 13.

> ☁️ **No ar (Azure):** https://agrosat-api-566067.azurewebsites.net/swagger-ui/index.html
> — login `admin@agrosat.com.br` / `123456`. Detalhes do deploy em [`DEPLOY-NUVEM.md`](DEPLOY-NUVEM.md).

## Stack

- Java 17, Spring Boot 3.3, Maven
- Spring Data JPA / Hibernate (Oracle FIAP)
- Spring Security + JWT (jjwt)
- Bean Validation (DTOs como `record`)
- Swagger / OpenAPI (springdoc)

## Arquitetura (pacote `br.com.fiap.agrosat`)

```
entity/       -> 10 entidades JPA (espelham as tabelas Oracle do database/01_ddl.sql)
repository/   -> Spring Data JPA (JpaRepository)
dto/          -> Records de Request/Response + validação
service/      -> regras e mapeamento DTO <-> entidade
controller/   -> endpoints REST /api/...
security/     -> JwtService, filtro JWT, UserDetailsService
config/       -> SecurityConfig, OpenApiConfig, DataSeeder (perfil h2)
exception/    -> tratamento global de erros
```

### As 10 entidades / recursos

| Entidade        | Recurso REST          |
|-----------------|-----------------------|
| Usuario         | `/api/usuarios`       |
| Propriedade     | `/api/propriedades`   |
| Talhao          | `/api/talhoes`        |
| Cultura         | `/api/culturas`       |
| Sensor          | `/api/sensores`       |
| LeituraSensor   | `/api/leituras`       |
| DadoSatelite    | `/api/dados-satelite` |
| AlertaAgricola  | `/api/alertas`        |
| Recomendacao    | `/api/recomendacoes`  |
| Irrigacao       | `/api/irrigacoes`     |

Cada recurso expõe: `GET /` (lista), `GET /{id}`, `POST /`, `PUT /{id}`, `DELETE /{id}`.
JSON em camelCase, datas em ISO-8601 (segue o `CONTRATO-DOMINIO.md`).

## Como rodar

### Opção A — teste rápido (H2 em memória, sem precisar do Oracle)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

- Não precisa de banco; o Hibernate cria as tabelas e um usuário de teste é criado automaticamente.
- Login de teste: **admin@agrosat.com.br** / senha **123456**
- Console do banco: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:agrosat`)

### Opção B — Oracle FIAP (perfil padrão)

1. Edite `src/main/resources/application-oracle.properties` e preencha:
   - `spring.datasource.url` (ex.: `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL`)
   - `spring.datasource.username` (seu RM)
   - `spring.datasource.password`
2. Rode o `database/01_ddl.sql` e o `02_dml.sql` no Oracle (tabelas já existem).
3. Suba a aplicação:

```bash
mvn spring-boot:run
```

## Autenticação (JWT)

1. Faça login:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@agrosat.com.br","senha":"123456"}'
```

Resposta:

```json
{ "token": "eyJhbGciOi...", "tipo": "Bearer", "email": "admin@agrosat.com.br", "expiraEm": 86400000 }
```

2. Use o token nas demais chamadas:

```bash
curl http://localhost:8080/api/talhoes \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

> Observação: usuários têm a senha gravada com BCrypt. Crie usuários via `POST /api/usuarios`
> (esse endpoint também exige token; no perfil H2 use o admin de teste para criar os demais).

## Swagger / OpenAPI

- UI: http://localhost:8080/swagger-ui.html
- JSON: http://localhost:8080/v3/api-docs
- No Swagger, clique em **Authorize** e cole o token (sem o prefixo `Bearer`).

## O que o Kaiky ainda precisa fazer manualmente

- [ ] **Credenciais Oracle**: preencher URL, usuário (RM) e senha em `application-oracle.properties`.
- [ ] **Segredo JWT**: trocar `agrosat.jwt.secret` no `application.properties` por uma chave própria (>= 32 caracteres).
- [ ] **Deploy público** (requisito da entrega): subir em **Render** ou **Railway**
      (ambos têm plano gratuito e fazem build Maven). Configure as variáveis de ambiente do banco
      e exponha a porta 8080. Dica: gerar o `.jar` com `mvn clean package` e usar um `Dockerfile`
      `eclipse-temurin:17-jdk` se preferir container.
- [ ] **Vídeo de 10 min**: demonstrar CRUD + login JWT + Swagger rodando (de preferência no deploy público).
- [ ] **Pitch de 3 min**: problema (agricultura/água/clima), solução AgroSat e ODS 2, 8, 9, 13.
- [ ] (Opcional) Criar usuários reais via `POST /api/usuarios` e validar as FKs com dados do `02_dml.sql`.
```
