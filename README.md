# Victor's Springrealm: A Jornada Java

![](.github/images/cover.jpg)

API de Jogo RPG desenvolvida com Spring Boot. Esta API permite a criação e gerenciamento de personagens, jogadores, inimigos, batalhas e histórico de batalhas para um jogo de RPG no estilo AD&D.

Acesse a API em produção: [https://api.victorborzaquel.com](https://api.victorborzaquel.com)  
Teste a API: [postman](https://www.postman.com/victorborzaquel/workspace/victor-s-springrealm-a-jornada-java/overview)

## Índice

1. [Visão Geral](#visão-geral)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Funcionalidades Principais](#funcionalidades-principais)
4. [Como Executar](#como-executar)
5. [Endpoints da API](#endpoints-da-api)
6. [Documentação Completa](#documentação-completa)
7. [Conclusão](#conclusão)

## Visão Geral

A API permite aos jogadores criar e gerenciar personagens, jogadores, inimigos, realizar batalhas entre eles e acompanhar o histórico dessas batalhas. Ela oferece um conjunto completo de operações CRUD (Create, Read, Update, Delete) para cada entidade, permitindo um controle detalhado sobre o jogo.

## Tecnologias Utilizadas

As principais tecnologias utilizadas nesta API incluem:

- **Spring Boot:** Um framework Java para desenvolvimento de aplicativos web e API.
- **Spring Data JPA:** Uma biblioteca do Spring para facilitar o acesso a dados com o Java Persistence API (JPA).
- **PostgreSQL:** Banco de dados usado para armazenar informações de personagens, jogadores, inimigos e histórico de batalhas.

## Funcionalidades Principais

A API oferece as seguintes funcionalidades principais:

1. **Cadastro de Personagens:** Crie novos personagens com detalhes como nome e tipo, quantidade de dados e quantas faces cada dado tem, atributos e habilidades.
2. **Cadastro de Jogadores:** Registre jogadores com informações como nome, username e personagem associado.
3. **Cadastro de Inimigos:** Adicione inimigos com informações como nome, slug e personagem associado.
4. **Realização de Batalhas:** Inicie batalhas entre personagens e inimigos, com cálculo de resultados com base em habilidades e atributos.
5. **Histórico de Batalhas:** Acompanhe o histórico de todas as batalhas realizadas, incluindo resultados e detalhes.

## Como Executar
Para executar a API, você tem duas opções disponíveis:

## Opção 1: Executando com o Docker
Você pode executar a API usando o Docker. Siga estas etapas:

1. Certifique-se de que você tenha o Docker instalado em sua máquina.

2. Abra um terminal e execute o seguinte comando para baixar a imagem Docker do seu projeto:

   ```bash
   docker pull victorborzaquel/springrealm
   ```

3. Em seguida, inicie um contêiner Docker com a imagem baixada usando o seguinte comando:

   ```bash
   docker run -d -p 9988:9988 victorborzaquel/springrealm
   ```
4. Aguarde um minuto até que a API esteja completamente inicializada.

A API estará disponível em [http://localhost:9988](http://localhost:9988).

## Opção 2: Executando localmente

Para executar a API em sua máquina local, siga estas etapas:


1. Clone este repositório:

   ```bash
   git clone https://github.com/victorborzaquel/springrealm.git
   ```

2. Importe o projeto em sua IDE preferida, como o Intellij ou VSCode.

3. Escolha uma das duas opções a baixo para rodar o banco de dados:

   1. Caso tenha o docker instalado, execute o comando:

      ```bash
      docker-compose up -d
      ```

   2. Caso tenha o banco de dados PostgreSQL instalado, Configure as informações do banco de dados no arquivo `application-dev.yml`.

4. Execute o projeto Spring Boot.

A API estará disponível em [http://localhost:9988](http://localhost:9988).

## Endpoints da API

A API possui os seguintes endpoints:  

### Jogador (`/players`)

- **POST** `/`: Crie um jogador.
- **GET** `/`: Busque todos os jogadores.
- **GET** `/{id}`: Busque um jogador pelo id.
- **GET** `/username/{username}`: Busque um jogador pelo username.
- **PUT** `/{id}`: Edição de um jogador pelo id.
- **PUT** `/{username}`: Edição de um jogador pelo username.
- **DELETE** `/{id}`: Delete um jogador pelo id.
- **DELETE** `/username/{username}`: Delete um jogador pelo username.

### Inimigo (`/enemies`)

- **POST** `/`: Crie um inimigo.
- **GET** `/`: Busque todos os inimigos.
- **GET** `/{id}`: Busque um inimigo pelo id.
- **GET** `/slug/{slug}`: Busque um inimigo pelo slug.
- **PUT** `/{id}`: Edição de um inimigo pelo id.
- **PUT** ` /slug/{slug}`: Edição de um inimigo pelo slug.

### Personagem (`/characters`)

- **POST** `/`: Crie um personagem.
- **GET** `/`: Busque todos os personagens.
- **GET** `/type`: Busque todos os personagens de um tipo específico.
- **GET** `/{id}`: Busque um personagem pelo id.
- **GET** `/slug/{slug}`: Busque um personagem pelo slug.
- **PUT** `/{id}`: Edição de um personagem pelo id.
- **PUT** `/{slug}`: Edição de um personagem pelo slug.

### Histórico (`/histories`)

- **GET** `/`: Busque todas as batalhas.
- **GET** `/player/{username}`: Busque todas as batalhas de um jogador.
- **GET** `/{id}`: Busque todas as informações de uma batalha pelo id.

### Batalha (`/battles`)

- **POST** `/current`: Busque a batalha atual.
- **POST** `/start`: Inicie uma batalha.
- **POST** `/start/random`: Inicie uma batalha aleatória.
- **POST** `/initial`: Jogue os dados iniciais para ver quem começa a batalha.
- **POST** `/attack`: Ataque um inimigo.
- **POST** `/defense`: Defenda um ataque.

## Documentação Completa

Para obter informações detalhadas sobre os endpoints, modelos de dados, solicitações e respostas, consulte as documentações detalhadas:

- [Postman](https://www.postman.com/victorborzaquel/workspace/victor-s-springrealm-a-jornada-java/overview)
- [Swagger](https://api.victorborzaquel.com/swagger-ui/index.html)

## Conclusão

Esta API de Jogo RPG no estilo AD&D é uma demonstração do meu trabalho e habilidades de desenvolvimento com Spring Boot. Sinta-se à vontade para explorá-la e entre em contato comigo se precisar de mais informações ou tiver alguma pergunta.
