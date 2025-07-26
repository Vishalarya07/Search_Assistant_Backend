package com.searchassistant.search_assistant_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*") // Allow React dev server
public class SearchController {
    private final QwenAiService qwenAiService;

    @Autowired
    public SearchController(QwenAiService qwenAiService) {
        this.qwenAiService = qwenAiService;
    }

    @PostMapping
    public Object search(@RequestBody SearchRequest request) {
        try {
            return qwenAiService.search(request.getQuery());
        } catch (Exception e) {
            e.printStackTrace(); // Log the error to the backend terminal
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", e.getMessage());
            return error;
        }
    }

    public static class SearchRequest {
        private String query;
        public String getQuery() { return query; }
        public void setQuery(String query) { this.query = query; }
    }
} 