package com.regrex.dailyJokes.model;

/**
 * Created by surinder on 22-Jun-17.
 */

public class CategorySingle {

    public CategorySingle(int categoryId, String categoryNameHindi, String categoryEnglish) {
        this.categoryNameHindi = categoryNameHindi;
        this.categoryEnglish = categoryEnglish;
        this.categoryId = categoryId;
    }

    public CategorySingle() {
    }

    public String categoryNameHindi, categoryEnglish;
    public int categoryId;

}
