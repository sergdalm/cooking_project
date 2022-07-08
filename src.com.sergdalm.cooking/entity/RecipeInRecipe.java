package entity;

public class RecipeInRecipe {
    private Integer recipeId;
    private Integer usedRecipeId;
    private Integer gram;
    private String notes;

    public RecipeInRecipe() {
    }

    public RecipeInRecipe(Integer recipeId, Integer usedRecipeId, Integer gram, String notes) {
        this.recipeId = recipeId;
        this.usedRecipeId = usedRecipeId;
        this.gram = gram;
        this.notes = notes;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getUsedRecipeId() {
        return usedRecipeId;
    }

    public void setUsedRecipeId(Integer usedRecipeId) {
        this.usedRecipeId = usedRecipeId;
    }

    public Integer getGram() {
        return gram;
    }

    public void setGram(Integer gram) {
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
        return "RecipeInRecipe{" +
                "recipeId=" + recipeId +
                ", usedRecipeId=" + usedRecipeId +
                ", gram=" + gram +
                ", notes='" + notes + '\'' +
                '}';
    }
}
