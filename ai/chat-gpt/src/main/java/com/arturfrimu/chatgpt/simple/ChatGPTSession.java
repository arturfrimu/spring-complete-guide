package com.arturfrimu.chatgpt.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// This class manages a session with OpenAI's ChatGPT API.
public class ChatGPTSession {
    private static final String OPENAI_URL = "https://api.openai.com/v1/engines/";
    private static final int MAX_TOKENS = 4096; // Imposta questo valore in base al limite del modello
    private static final int MAX_TOKENS_PER_MESSAGE = 100; // Numero massimo di token per messaggio
    private String apiKey;
    private String botId;
    private StringBuilder conversationContext;

    public ChatGPTSession(String apiKey, String botId) {
        this.apiKey = apiKey;
        this.botId = botId;
        this.conversationContext = new StringBuilder();
    }

    public String sendMessage(String message) {
        // Aggiungi il nuovo messaggio al contesto
        conversationContext.append(message).append("\n");

        // Tronca il contesto se supera i limiti di token
        truncateContext();
        //https://chat.openai.com/g/g-MSR4IZBfU-xtalk-ict-chatbot
        // Costruisci e invia la richiesta
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(OPENAI_URL + botId + "/completions");
            request.setHeader("Authorization", "Bearer " + apiKey);
            request.setHeader("Content-Type", "application/json");

            ChatGPTPrompt pt = new ChatGPTPrompt();
            pt.setMax_tokens("" + MAX_TOKENS_PER_MESSAGE);
            pt.setPrompt(conversationContext.toString());

            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(pt);

            request.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                // Aggiungi la risposta al contesto
                conversationContext.append(responseBody).append("\n");
                return responseBody;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Metodo per troncare il contesto se necessario
    private void truncateContext() {
        String context = conversationContext.toString();
        String[] tokens = context.split("\\s+");
        if (tokens.length > MAX_TOKENS) {
            // Conserva solo gli ultimi MAX_TOKENS token
            StringBuilder truncated = new StringBuilder();
            for (int i = tokens.length - MAX_TOKENS; i < tokens.length; i++) {
                truncated.append(tokens[i]).append(" ");
            }
            conversationContext = new StringBuilder(truncated.toString().trim());
        }
    }

    public static void main(String[] args) throws IOException {
        String model = "ft:gpt-3.5-turbo-1106:personal::8UHr0aYv";
        String promptMessage = "A quanti anni e morto Einstain?";
        String data = "{\"model\":\"" + model + "\", \"messages\":[{\"role\":\"system\",\"content\":\"Questo è un chatbot aziendale.\"},{\"role\":\"user\",\"content\":\"" + promptMessage + "\"}]}";

        URL url = new URL("https://api.openai.com/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + "YOUR_A_P_I____K_E_Y");
        conn.setDoOutput(true);

        conn.getOutputStream().write(data.getBytes());

        Scanner scanner;
        StringBuilder response;
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            scanner = new Scanner(conn.getErrorStream());
            response = new StringBuilder("Error: ");
        } else {
            scanner = new Scanner(conn.getInputStream());
            response = new StringBuilder("Response: ");
        }
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
            response.append("\n");
        }
        scanner.close();

        System.out.println(response);
    }
}
