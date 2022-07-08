package entity;

public class RecipeSpice {
    private Integer recipeId;
    private Integer spiceId;
    private String notes;

    public RecipeSpice() {
    }

    public RecipeSpice(Integer recipeId, Integer spiceId, String notes) {
        this.recipeId = recipeId;
        this.spiceId = spiceId;
        this.notes = notes;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public Integer getSpiceId() {
        return spiceId;
    }

    public void setSpiceId(Integer spiceId) {
        this.spiceId = spiceId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "RecipeSpice{" +
                "recipeId=" + recipeId +
                ", spiceId=" + spiceId +
                ", note='" + notes + '\'' +
                '}';
    }
}
