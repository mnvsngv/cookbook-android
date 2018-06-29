package com.mnvsngv.cookbook.models;

import com.mnvsngv.cookbook.util.Utils;

import java.util.List;

public class Recipe {
    private String name;
    private List<String> ingredients;
    private List<String> spices;
    private String steps;

    public Recipe(String recipeName, String ingredients, String spices, String steps) {
        this.name = recipeName;
        this.ingredients = Utils.convertCsvToList(ingredients);
        this.spices = Utils.convertCsvToList(spices);
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSpices() {
        return spices;
    }

    public void setSpices(List<String> spices) {
        this.spices = spices;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
