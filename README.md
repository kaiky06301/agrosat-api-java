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

---

# 🐳 DevOps Tools & Cloud Computing — How to (rodar em nuvem)

> Disciplina **DevOps & Cloud**. Ambiente **conteinerizado** com **2 containers Docker** (API + banco)
> rodando em **VM Linux na Microsoft Azure** (NÃO localhost). Imagem da aplicação gerada via Dockerfile.

## Descrição da solução
**AgroSat** é uma solução de **agricultura de precisão**: cruza **dados de satélite** (NDVI, umidade
estimada, previsão de chuva) com **sensores ESP32 no campo** (umidade do solo, temperatura) e gera
**alertas e recomendações de irrigação** por talhão. Esta entrega **conteineriza a API Java** e a sobe
na nuvem junto de um banco **PostgreSQL**, ambos em containers Docker na mesma rede. ODS 2, 8, 9, 13.

## 🗺️ Arquitetura macro
Diagrama (Draw.io): [`arquitetura-devops.drawio`](arquitetura-devops.drawio)

```
                       Microsoft Azure — VM Ubuntu (northcentralus) — IP público (NÃO localhost)
                       ┌──────────────────────────────────────────────────────────────────┐
 App Mobile  ─┐        │   rede docker: agrosat-net-566067                                  │
 Navegador   ─┼─HTTP→  │   ┌───────────────────────────┐      ┌──────────────────────────┐ │
 ESP32 (IoT) ─┤  :8080 │   │ agrosat-app-566067        │JDBC  │ agrosat-db-566067        │ │
 Satélite    ─┘        │   │ Spring Boot (imagem própria)│────→│ postgres:16 (img pública)│ │
                       │   │ não-root: agrosat, /app    │:5432 │ volume: pgdata-566067    │ │
                       │   │ porta 8080                 │      │ 10 tabelas + relações    │ │
                       │   └───────────────────────────┘      └──────────────────────────┘ │
                       └──────────────────────────────────────────────────────────────────┘
```

## ▶️ How-to — do clone até os testes em nuvem
Pré-requisitos na VM (Ubuntu): Docker. Os comandos abaixo usam o **RM 566067** no nome dos containers.

```bash
# 1) Clonar o projeto
git clone https://github.com/kaiky06301/agrosat-api-java.git
cd agrosat-api-java

# 2) Gerar a imagem PERSONALIZADA da aplicação a partir do Dockerfile
docker build -t agrosat-api:566067 .

# 3) Rede Docker e volume nomeado
docker network create agrosat-net-566067
docker volume  create pgdata-566067

# 4) Container do BANCO (imagem pública, volume nomeado, env, porta, RM no nome)
docker run -d --name agrosat-db-566067 --network agrosat-net-566067 \
  -e POSTGRES_DB=agrosat -e POSTGRES_USER=agrosat -e POSTGRES_PASSWORD=agrosat2026 \
  -v pgdata-566067:/var/lib/postgresql/data -p 5432:5432 postgres:16

# 5) Container da APLICAÇÃO (imagem própria, não-root, env, porta, RM no nome, mesma rede)
docker run -d --name agrosat-app-566067 --network agrosat-net-566067 \
  -e SPRING_PROFILES_ACTIVE=postgres \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://agrosat-db-566067:5432/agrosat \
  -e SPRING_DATASOURCE_USERNAME=agrosat -e SPRING_DATASOURCE_PASSWORD=agrosat2026 \
  -e AGROSAT_JWT_SECRET=<sua-chave-256-bits> -e JAVA_OPTS="-Xms128m -Xmx384m" \
  -p 8080:8080 agrosat-api:566067

# 6) Conferir os 2 containers em background + logs
docker ps
docker logs agrosat-db-566067
docker logs agrosat-app-566067

# 7) Entrar nos containers (docker exec): diretório e usuário
docker exec agrosat-app-566067 sh -c "pwd; whoami; ls -l"   # -> /app | agrosat (não-root)
docker exec -it agrosat-db-566067 psql -U agrosat -d agrosat -c "\dt"
```

## 🧪 Testes do CRUD na nuvem (substitua pelo IP público da VM)
```bash
BASE=http://<IP-PUBLICO-DA-VM>:8080

# Login -> JWT (admin criado pelo seeder no 1º start)
TOKEN=$(curl -s -X POST $BASE/api/auth/login -H "Content-Type: application/json" \
  -d '{"email":"admin@agrosat.com.br","senha":"123456"}' | sed -E 's/.*"token":"([^"]+)".*/\1/')

# CREATE / READ / UPDATE / DELETE de Propriedade
curl -s -X POST   $BASE/api/propriedades   -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{"idUsuario":1,"nome":"Fazenda Boa Vista","municipio":"Ribeirao Preto","uf":"SP","areaTotalHa":120.5}'
curl -s            $BASE/api/propriedades   -H "Authorization: Bearer $TOKEN"
curl -s -X PUT     $BASE/api/propriedades/1 -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{"idUsuario":1,"nome":"Fazenda Boa Vista (editada)","areaTotalHa":99.9}'
curl -s -X DELETE  $BASE/api/propriedades/1 -H "Authorization: Bearer $TOKEN"
```

## 🔎 Evidência obrigatória — SELECT direto no banco (persistência em 2+ tabelas)
```bash
docker exec -it agrosat-db-566067 psql -U agrosat -d agrosat \
  -c "SELECT p.id_propriedade, p.nome AS fazenda, u.nome AS dono, u.email
      FROM propriedade p JOIN usuario u ON u.id_usuario = p.id_usuario;"
```

## ✅ Conformidade com os requisitos da disciplina
- 2 containers em **nuvem** (não localhost), em **background**, na **mesma rede** Docker.
- App: **imagem própria via Dockerfile**, **usuário não-root** (`agrosat`), **WORKDIR** `/app`,
  **variável de ambiente**, **porta 8080** exposta, **RM no nome** (`agrosat-app-566067`).
- Banco: **imagem pública** `postgres:16`, **volume nomeado** (`pgdata-566067`), **variável de ambiente**,
  **porta 5432** exposta, **RM no nome** (`agrosat-db-566067`), **10 tabelas com relacionamentos**.
- **CRUD completo** (Create/Read/Update/Delete) persistindo em **≥2 tabelas** (usuário ↔ propriedade).
- Evidência de **SELECT** conectado diretamente no container do banco.

> Alternativa de execução: `docker compose up -d --build` usando o [`docker-compose.yml`](docker-compose.yml)
> (sobe os mesmos 2 containers, app + Postgres, na mesma rede).
