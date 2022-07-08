package entity;

public class UserRecipe {
    private Integer userId;
    private Integer recipeId;

    public UserRecipe() {
    }

    public UserRecipe(Integer userId, Integer recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "UserRecipe{" +
                "userId=" + userId +
                ", recipeId=" + recipeId +
                '}';
    }
}
