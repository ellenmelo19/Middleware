package com.exemplo.middleware;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class Middleware {

    private final ThirdPartyAPI thirdPartyAPI;
    public Middleware(ThirdPartyAPI thirdPartyAPI, ResponseAPI responseAPI) {
        this.thirdPartyAPI = thirdPartyAPI;
    }

    public Object handleRequest(Request request, Response response) {
        String endpoint = "/recurso"; // Defina o endpoint da API de terceiros
        try {
            // Faz a chamada à API de terceiros para obter a resposta em JSON
            JSONArray jsonArray = thirdPartyAPI.doGet(endpoint);

            // Manipula os dados recebidos, conforme necessário
            JSONArray transformedArray = transformData(jsonArray);

            // Retorna a resposta tratada em formato JSON
            response.type("application/json");
            return transformedArray.toString();
        } catch (APIException e) {
            response.status(500);
            return "Erro ao processar a solicitação.";
        } catch (Exception e) {
            response.status(500);
            return "Erro interno no servidor.";
        }
    }

    // Método para manipular os dados recebidos (exemplo)
    private JSONArray transformData(JSONArray jsonArray) {
        JSONArray transformedArray = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            // Faça a manipulação necessária dos dados
            transformedArray.put(obj);
        }
        return transformedArray;
    }
}
