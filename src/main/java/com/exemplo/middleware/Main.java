package com.exemplo.middleware;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // Configuração das dependências do middleware
        String apiUrl = "https://api.openweathermap.org";
        ThirdPartyAPI thirdPartyAPI = new ThirdPartyAPI(apiUrl, null); // Adicione os cabeçalhos de autenticação, se necessário
        ResponseAPI responseAPI = new ResponseAPI("https://jsonplaceholder.typicode.com"); // URL da outra API para enviar a resposta tratada

        // Criação do middleware com as dependências configuradas
        Middleware middleware = new Middleware(thirdPartyAPI, responseAPI);

        // Configuração do Spark para criar uma rota que lida com as requisições HTTP
        port(8080); // Define a porta em que o servidor irá escutar

        // Criar uma variável final para armazenar o valor da variável middleware
        final Middleware finalMiddleware = middleware;
        
        get("/middleware", (request, response) -> {
            // Usar a variável finalMiddleware dentro da expressão lambda
            Object responseData = finalMiddleware.handleRequest(request, response);
            response.type("application/json");
            return responseData;
        });
        
    }
}
