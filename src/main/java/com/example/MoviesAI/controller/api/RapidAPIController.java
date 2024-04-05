package com.example.MoviesAI.controller.api;

import com.example.MoviesAI.service.api.RapidAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class RapidAPIController {

    private final RapidAPIService rapidAPIService;

    @Autowired
    private RapidAPIController(RapidAPIService rapidAPIService) {
        this.rapidAPIService = rapidAPIService;
    }


    @GetMapping("/search-imdb")
    public String searchIMDB(@RequestParam String id) throws ExecutionException, InterruptedException {
        return rapidAPIService.searchIMDB(id).get();
    }
}
