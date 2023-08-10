# Projeto de Middleware

Este é um projeto de middleware em Java que atua como uma camada intermediária para integrar com APIs de terceiros, manipular os dados recebidos e enviar respostas tratadas através de outra API. O middleware utiliza o framework Spark para criar um servidor web que lida com requisições HTTP.

## Funcionalidades

- Faz chamadas HTTP GET para uma API de terceiros e manipula os dados recebidos.
- Envia a resposta tratada para outra API usando chamadas HTTP POST.
- Implementa testes de unidade utilizando o framework Mockito.

## Configuração

1. Clone o repositório para o seu ambiente local.

2. Certifique-se de ter o Java JDK e o Maven instalados.

3. Abra o projeto em sua IDE de escolha.

4. Configure as URLs das APIs de terceiros e outras opções de configuração na classe `Main.java`.

5. Execute o projeto. O servidor Spark estará rodando na porta 8080.

## Estrutura do Projeto

- `src/main/java/com/exemplo/middleware`: Contém as classes principais do projeto.
  - `Main.java`: Configuração do servidor Spark e criação do middleware.
  - `Middleware.java`: Lógica central do middleware para manipulação das requisições.
  - `ThirdPartyAPI.java`: Integração com a API de terceiros.
  - `ResponseAPI.java`: Envio da resposta tratada para outra API.
  - `APIException.java`: Classe de exceção personalizada para problemas de API.
- `src/test/java/com/exemplo/middleware`: Contém os testes unitários.
  - `MiddlewareTest.java`: Testes usando o framework Mockito.

## Como Usar

1. Configure as URLs das APIs de terceiros na classe `Main.java`.

2. Execute o projeto. O servidor Spark estará rodando na porta 8080.

3. Faça requisições HTTP para `http://localhost:8080/middleware` para testar o middleware.

## Testes

O projeto inclui testes de unidade usando o framework Mockito para verificar o comportamento do middleware em diferentes cenários. Os testes estão localizados no diretório `src/test/java`.

## Avisos

Este projeto é apenas um exemplo educacional e NÃO É adequado para ambientes de produção. Além disso o projeto ainda apresenta certos erros na área de testes unitários.

## Licença

 Licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.

