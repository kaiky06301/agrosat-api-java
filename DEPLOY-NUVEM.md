# ☁️ Deploy na Nuvem — AgroSat API (Azure)

API Java (Spring Boot) do projeto **AgroSat** rodando **em container Docker na nuvem Azure**,
com URL pública. Global Solution FIAP 2026/1 — disciplinas **Java Advanced** e **DevOps Tools & Cloud Computing**.

---

## 🌐 Ambiente publicado (no ar)

| Item | Valor |
|------|-------|
| **Swagger (UI)** | https://agrosat-api-566067.azurewebsites.net/swagger-ui/index.html |
| **OpenAPI (JSON)** | https://agrosat-api-566067.azurewebsites.net/v3/api-docs |
| **Login (obter JWT)** | `POST /api/auth/login` |
| **Usuário de teste** | `admin@agrosat.com.br` / `123456` |

> Banco: **H2 em memória** (perfil `h2`). O `DataSeeder` recria o usuário admin a cada start.
> O `docker-compose.yml` (API + PostgreSQL) está no repositório para execução local com 2 containers.

### Teste rápido (curl)
```bash
BASE=https://agrosat-api-566067.azurewebsites.net

# 1) Login -> retorna o token JWT
curl -X POST "$BASE/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@agrosat.com.br","senha":"123456"}'

# 2) Usar o token num endpoint protegido
TOKEN=<cole_o_token>
curl "$BASE/api/propriedades" -H "Authorization: Bearer $TOKEN"
```

---

## 🏗️ Arquitetura do deploy

```
  Imagem Docker (Dockerfile)                Azure
  da API Java Spring Boot      ───push──>  ┌─────────────────────────────┐
  (build local, linux/amd64)               │ Azure Container Registry     │
                                           │   acragrosat566067           │
                                           │     agrosat-api:latest       │
                                           └──────────────┬──────────────┘
                                                          │ pull
                                           ┌──────────────▼──────────────┐
                                           │ Azure App Service (Linux B1) │
                                           │   agrosat-api-566067         │
                                           │   container :8080  (perfil h2)│
                                           └──────────────┬──────────────┘
                                                          │ HTTPS público
                                                  https://agrosat-api-566067.azurewebsites.net
```

**Por que App Service + ACR (e não VM Linux):** a subscrição *Azure for Students* usada está com
**cota de VM = 0** em todas as famílias/regiões e com o *build na nuvem* (ACR Tasks) bloqueado.
O mesmo conceito da disciplina (imagem Docker → container → URL pública na nuvem) foi entregue
via **Azure App Service for Containers**, que a conta libera. A imagem é a mesma; muda só o serviço Azure que a executa.

---

## 🔁 Como reproduzir o deploy

Pré-requisitos: `az` (Azure CLI) logado, Docker (Colima/Docker Desktop), Maven.

```bash
# Variáveis
RG=rg-agrosat-braz
ACR=acragrosat566067
PLAN=plan-agrosat
APP=agrosat-api-566067
IMG=$ACR.azurecr.io/agrosat-api:latest

# 0) Providers (1ª vez na conta)
az provider register --namespace Microsoft.ContainerRegistry
az provider register --namespace Microsoft.Web

# 1) Resource Group + Container Registry
az group create -n $RG -l brazilsouth
az acr create -g $RG -n $ACR --sku Basic --admin-enabled true

# 2) Build do jar e da imagem (linux/amd64) + push
mvn clean package -DskipTests
docker build --platform linux/amd64 -t $IMG .     # ./Dockerfile (multi-stage)
az acr login -n $ACR
docker push $IMG

# 3) App Service (Linux B1) + Web App apontando para a imagem
az appservice plan create -g $RG -n $PLAN --is-linux --sku B1
az webapp create -g $RG -p $PLAN -n $APP --deployment-container-image-name $IMG

# 4) Credenciais do ACR + porta + perfil + segredo JWT
ACR_USER=$(az acr credential show -n $ACR --query username -o tsv)
ACR_PASS=$(az acr credential show -n $ACR --query "passwords[0].value" -o tsv)
az webapp config container set -g $RG -n $APP \
  --container-image-name $IMG \
  --container-registry-url https://$ACR.azurecr.io \
  --container-registry-user $ACR_USER \
  --container-registry-password $ACR_PASS
az webapp config appsettings set -g $RG -n $APP --settings \
  WEBSITES_PORT=8080 SPRING_PROFILES_ACTIVE=h2 AGROSAT_JWT_SECRET=<sua-chave>
az webapp restart -g $RG -n $APP
```

> Numa conta Azure **sem** a trava de VM, dá para seguir o método da aula (VM Linux + Docker):
> ver `deploy-vm.sh` (instala Docker, clona o repo e roda `docker compose up -d --build` na VM).

---

## 💸 Encerrar (após a apresentação)

```bash
az group delete -n rg-agrosat-braz --yes --no-wait   # remove App Service + ACR (para de gastar crédito)
```
