package com.arturfrimu.chatgpt.mysimpleversion;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTService {
    @Value("${openai.url}")
    private String openAiUrl;

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.bot-id}")
    private String botId;

    public String sendMessage(String message) {
        String url = openAiUrl + botId + "/completions";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", "Bearer " + apiKey);
            request.setHeader("Content-Type", "application/json");

            String jsonBody = "{\"prompt\": \"" + message + "\", \"max_tokens\": 100}";
            request.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send message: " + e.getMessage();
        }
    }
}

