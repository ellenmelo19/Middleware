/*
 * Nome do Arquivo: ThirdPartyAPI.java
 * Descrição: Classe que realiza integração com API de terceiros.
 * Autor: Ellen Melo
 * Data de Criação: 29 de Julho de 2023
 */

package com.exemplo.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;

/**
 * Classe que representa uma API de terceiros e realiza a integração com ela.
 * Oferece métodos para fazer chamadas HTTP GET, POST, PUT, etc.
 */
public class ThirdPartyAPI {
    private final String apiUrl;
    private final Map<String, String> headers;
    private int connectionTimeout = 5000; // Tempo limite de conexão padrão (5 segundos)
    // Construtor com autenticação
    public ThirdPartyAPI(String apiUrl, Map<String, String> headers) {
        this.apiUrl = apiUrl;
        this.headers = headers;
    }

    /**
     * Faz uma chamada HTTP GET à API de terceiros com autenticação.
     * @param endpoint O endpoint da API a ser chamado.
     * @return Um JSONArray contendo os dados recebidos da API.
     * @throws APIException se ocorrer um erro na chamada à API.
     */
    public JSONArray doGet(String endpoint) throws APIException {
        String url = apiUrl + endpoint;
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            // Definir tempo limite de conexão
            connection.setConnectTimeout(connectionTimeout);

            // Adicionar cabeçalhos de autenticação
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            } else {
                throw new APIException("Cabeçalhos de autenticação não foram configurados.");
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Faz o parsing do JSON recebido
                JSONArray jsonArray = new JSONArray(response.toString());
                return jsonArray;
            } else {
                throw new APIException("Erro na chamada HTTP: " + responseCode);
            }
        } catch (Exception e) {
            throw new APIException("Erro durante a chamada à API: " + e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // Métodos para configurar opções de configuração

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void enableCache(boolean enableCache) {
    }

    // Outros métodos para chamadas HTTP POST, PUT, DELETE, etc. (se necessário)
    // ...
}

