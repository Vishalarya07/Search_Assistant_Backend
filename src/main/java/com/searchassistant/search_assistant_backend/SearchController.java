package com.searchassistant.search_assistant_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:3000") // Allow React dev server
public class SearchController {
    private final QwenAiService qwenAiService;

    @Autowired
    public SearchController(QwenAiService qwenAiService) {
        this.qwenAiService = qwenAiService;
    }

    @PostMapping
    public String search(@RequestBody SearchRequest request) {
        return qwenAiService.search(request.getQuery());
    }

    public static class SearchRequest {
        private String query;
        public String getQuery() { return query; }
        public void setQuery(String query) { this.query = query; }
    }
} 