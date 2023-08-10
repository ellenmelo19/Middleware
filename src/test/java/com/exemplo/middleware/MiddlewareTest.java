package com.exemplo.middleware;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.junit.Assert.*;

public class MiddlewareTest {

    private ThirdPartyAPI mockThirdPartyAPI;
    private ResponseAPI mockResponseAPI;
    private Middleware middleware;

    @Before
    public void setUp() {
        // Cria instâncias simuladas de ThirdPartyAPI e ResponseAPI para teste
        mockThirdPartyAPI = mock(ThirdPartyAPI.class);
        mockResponseAPI = mock(ResponseAPI.class);
        // Cria uma nova instância de Middleware com as instâncias de ThirdPartyAPI e ResponseAPI
        middleware = new Middleware(mockThirdPartyAPI, mockResponseAPI);
    }

    @Test
    public void testHandleRequestSuccess() throws Exception {
        // Testa a resposta bem-sucedida do método handleRequest
         // Esperado: O método deve retornar uma resposta da outra API com status 200.
    
         // Simula a resposta da API externa para o teste
        JSONArray mockJsonArray = new JSONArray();
        mockJsonArray.put(new JSONObject().put("nome", "Teste").put("idade", 30));
    
        // Define a resposta esperada da outra API para o teste
        String expectedResponseFromOtherAPI = "Success";
    
        // Simula a resposta da API externa para o teste
        when(mockThirdPartyAPI.doGet(anyString())).thenReturn(mockJsonArray);
        when(mockResponseAPI.sendResponse(anyString())).thenReturn(expectedResponseFromOtherAPI);
    
        // Cria objetos fictícios para Request e Response
        Request mockRequest = mock(Request.class);
        Response mockResponse = mock(Response.class);
    
        // Chama o método em teste e trata a exceção
        Object response = middleware.handleRequest(mockRequest, mockResponse);
    
       // Verifica se o resultado é o esperado
        assertNotNull(response);
        assertEquals(expectedResponseFromOtherAPI, response);
    }

    @Test
    public void testHandleRequestAPIException() {
        // Simula uma exceção ao chamar a API de terceiros para o teste
        try {
            when(mockThirdPartyAPI.doGet(anyString())).thenThrow(new APIException("Erro na API de terceiros"));

            // Cria objetos fictícios para Request e Response
            Request mockRequest = mock(Request.class);
            Response mockResponse = mock(Response.class);

            // Chama o método em teste
            Object response = middleware.handleRequest(mockRequest, mockResponse);

            // Verifica se a resposta é a esperada para uma APIException
            assertEquals("Erro ao processar a solicitação.", response);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testHandleRequestOtherException() {
       // Simula uma exceção desconhecida ao manipular os dados para o teste
        try {
            when(mockThirdPartyAPI.doGet(anyString())).thenReturn(new JSONArray());
            when(mockResponseAPI.sendResponse(anyString())).thenThrow(new RuntimeException("Exceção desconhecida"));

           // Cria objetos fictícios para Request e Response
            Request mockRequest = mock(Request.class);
            Response mockResponse = mock(Response.class);

            // Chama o método em teste e trata a exceção
            Object response = middleware.handleRequest(mockRequest, mockResponse);

            // Verifica se a resposta é a esperada para a exceção desconhecida
            assertEquals("Erro interno no servidor.", response);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }
}
