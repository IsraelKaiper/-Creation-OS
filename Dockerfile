# Usa a imagem oficial do OpenJDK 11 como base
FROM openjdk:11-jre-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR construído pelo Maven para o contêiner
COPY target/sua-aplicacao.jar /app/app.jar

# Expõe a porta 8080 (ou a porta que a sua aplicação Spring Boot utiliza)
EXPOSE 8080

# Comando para executar a aplicação Spring Boot quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]