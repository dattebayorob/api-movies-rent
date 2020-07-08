### Api Movies Rent

## Inicialização via Docker Compose
docker network create net_app
docker-compose up --build

## Tecnologias Utilizadas 
 - Java JDK 11
 - Spring Boot
 - Spring Data Jpa com Hibernate
 - Spring Security 
 - Flyway para versionamento dos esquemas do banco
 - Lombok ( para geração dos getters,setters, e na injeção de dependencias pelo construtor)
 - Junit e Mockito para testes unitários 

## Security
 - Simples implementação de uma autenticação via header, apenas pra demonstrar o fluxo.
 - Usuários padrões 'administrator' e 'user' criados.