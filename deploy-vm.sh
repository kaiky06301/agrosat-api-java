#!/usr/bin/env bash
# ============================================================
# AgroSat — Provisionamento da VM Linux (Azure) — DevOps
# Roda DENTRO da VM Ubuntu. Instala Docker, clona o repo e
# sobe os 2 containers (API Java + PostgreSQL) com docker compose.
# ============================================================
set -euo pipefail

REPO="https://github.com/kaiky06301/agrosat-api-java.git"
DIR="agrosat-api-java"

echo "==> Atualizando pacotes"
sudo apt-get update -y

echo "==> Instalando Docker + plugin compose"
sudo apt-get install -y ca-certificates curl git
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable" \
  | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo usermod -aG docker "$USER" || true

echo "==> Clonando o repositório"
rm -rf "$DIR"
git clone "$REPO" "$DIR"
cd "$DIR"

echo "==> Subindo os containers (build + up)"
sudo docker compose up -d --build

echo "==> Aguardando a API responder (até 3 min)"
for i in $(seq 1 36); do
  if curl -fsS http://localhost:8080/swagger-ui/index.html >/dev/null 2>&1 \
     || curl -fsS http://localhost:8080/v3/api-docs >/dev/null 2>&1; then
    echo "API no ar!"
    break
  fi
  sleep 5
done

echo "==> Containers:"
sudo docker compose ps
echo ""
echo "==> Pronto. API pública em: http://<IP-DA-VM>:8080/swagger-ui/index.html"
