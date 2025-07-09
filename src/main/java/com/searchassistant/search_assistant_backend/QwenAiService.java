package com.searchassistant.search_assistant_backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class QwenAiService {
    @Value("${qwen.api.url}")
    private String qwenApiUrl;

    @Value("${qwen.api.key}")
    private String qwenApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String search(String query) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + qwenApiKey);

    Map<String, Object> body = new HashMap<>();
    body.put("model", "qwen/qwq-32b:free");
    List<Map<String, String>> messages = new ArrayList<>();
    Map<String, String> userMessage = new HashMap<>();
    userMessage.put("role", "user");
    userMessage.put("content", query);
    messages.add(userMessage);
    body.put("messages", messages);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(qwenApiUrl, request, String.class);

    // Parse the JSON and extract the assistant's message
    try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root.path("choices").get(0).path("message").path("content").asText();
    } catch (Exception e) {
        // fallback: return the whole response if parsing fails
        return response.getBody();
    }
}
}