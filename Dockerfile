# Usa a imagem oficial do OpenJDK 17 como base
FROM openjdk:17-jre-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Volume temporário usado para armazenar arquivos temporários
VOLUME /tmp

# Copia o arquivo JAR construído pelo Maven para o contêiner
COPY target/*.jar app.jar

# Comando para executar a aplicação Spring Boot quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expõe a porta 8080 (ou a porta que a sua aplicação Spring Boot utiliza)
EXPOSE 8080