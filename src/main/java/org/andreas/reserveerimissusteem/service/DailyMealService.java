package org.andreas.reserveerimissusteem.service;

import org.andreas.reserveerimissusteem.entity.Details;
import org.andreas.reserveerimissusteem.entity.Meal;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DailyMealService {
        private final RestClient restClient;

    public DailyMealService() {
        this.restClient = RestClient.create("https://www.themealdb.com");
    }

    public Meal getDaily() {
        Details details=this.restClient.get().uri("/api/json/v1/1/random.php").retrieve().body(Details.class);
        return details.meals().getFirst();
    }
}


