# ============================================================
# AgroSat — Dockerfile multi-stage da API Java (Spring Boot 3)
# Disciplina DevOps & Cloud — Global Solution FIAP 2026/1
#
# Stage 1 (build): compila o projeto Maven e gera o .jar
# Stage 2 (runtime): imagem enxuta só com JRE 17 + o .jar
#
# O contexto de build deve ser a pasta da API Java. No docker-compose.yml
# isso é configurado com:  build: { context: ../api-java, dockerfile: ../devops/Dockerfile }
# ============================================================

# ---------- Stage 1: BUILD (Maven + JDK 17) ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia primeiro só o pom.xml para aproveitar o cache de dependências do Docker.
# (se o pom não mudar, o Docker reusa a camada de download das dependências)
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Agora copia o código-fonte e empacota (pula testes para acelerar o build do container)
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---------- Stage 2: RUNTIME (apenas JRE 17) ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Usuário não-root por segurança
RUN useradd -r -u 1001 agrosat
USER agrosat

# Copia o .jar gerado no stage de build (nome vem de artifactId-version do pom: agrosat-api-1.0.0.jar)
COPY --from=build /app/target/*.jar app.jar

# Porta da API
EXPOSE 8080

# Perfil ativo e demais configs são injetados por variável de ambiente no docker-compose.
# Mantém o container respeitando limites de memória de plataformas gratuitas (Render/Railway).
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
