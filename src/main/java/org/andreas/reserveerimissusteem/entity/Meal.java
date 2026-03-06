package org.andreas.reserveerimissusteem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Meal(String strMeal, String strMealThumb){}