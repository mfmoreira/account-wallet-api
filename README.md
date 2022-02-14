# Wallet-Picpay-API
Solução Web Backend Java API para servir um aplicativo de carteira digital (Wallet).

Os usuários deste aplicativo realizam operações financeiras básicas como: 
	- Transferências de valores entre usuários
	- Saques
	- Depósitos 
	- Pagamento de contas

# Tecnologias Utilizadas

O Wallet API foi criado com:
 - Spring Boot
 - Spring Data JPA
 - Java 
 - MySQL
 - Dockerfile
 - Swagger
 - RestTemplate

# Arquitetura
Containers : 
- dbapp - MySql
- accountspringapp - API rest acount
- userspringapp - API rest users

O projeto Wallet API está divido nas seguintes camadas:

![alt text](https://github.com/mfmoreira/account-wallet-api/blob/main/img/wallet-picpay.drawio.png?raw=true)

main/java:

- Component
- Config
- Controller
- Converter
- Entities
- Enums
- Exception
- Mapper
- Model
- Repository
- Request
- Service

# Getting Started

Criando o JAR do aplicativo na pasta do Docker usando o plug-in maven
mvn clean install 

Tendo o docker instalado em sua máquina:

#App account-wallet-picpay

Entrar no diretório no projeto:
cd Docker/

Executar o comando:
docker-compose up –-build

-- Construindo o Spring Boot e a imagem MYSQL juntos

- Acessar o Swagger para uso dos endpoints
http://localhost:8090/swagger-ui.html#/

