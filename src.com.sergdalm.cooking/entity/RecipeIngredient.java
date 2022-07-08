package entity;

public class RecipeIngredient {
    private Integer recipeId;
    private Integer ingredientId;
    private int gram;
    private String notes;

    public RecipeIngredient() {
    }

    public RecipeIngredient(Integer recipeId, Integer ingredientId, int gram, String notes) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.gram = gram;
        this.notes = notes;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "recipeId=" + recipeId +
                ", ingredientId=" + ingredientId +
                ", gram=" + gram +
                ", notes='" + notes + '\'' +
                '}';
    }
}
