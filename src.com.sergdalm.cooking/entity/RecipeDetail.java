package entity;

public class RecipeDetail {
    private Integer recipeId;
    private Integer userId;
    private String description;
    private Integer activeCookingTimeMinute;
    private Integer totalCookingTimeMinute;
    private Integer numberOfPortions;

    public RecipeDetail() {
    }

    public RecipeDetail(Integer recipeId, Integer userId, String description, Integer activeCookingTimeMinute,
                        Integer totalCookingTimeMinute, Integer numberOfPortions) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.description = description;
        this.activeCookingTimeMinute = activeCookingTimeMinute;
        this.totalCookingTimeMinute = totalCookingTimeMinute;
        this.numberOfPortions = numberOfPortions;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getActiveCookingTimeMinute() {
        return activeCookingTimeMinute;
    }

    public void setActiveCookingTimeMinute(Integer activeCookingTimeMinute) {
        this.activeCookingTimeMinute = activeCookingTimeMinute;
    }

    public Integer getTotalCookingTimeMinute() {
        return totalCookingTimeMinute;
    }

    public void setTotalCookingTimeMinute(Integer totalCookingTimeMinute) {
        this.totalCookingTimeMinute = totalCookingTimeMinute;
    }

    public Integer getNumberOfPortions() {
        return numberOfPortions;
    }

    public void setNumberOfPortions(Integer numberOfPortions) {
        this.numberOfPortions = numberOfPortions;
    }

    @Override
    public String toString() {
        return "RecipeDetail{" +
                "recipeId=" + recipeId +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", activeCookingTimeMinute=" + activeCookingTimeMinute +
                ", totalCookingTimeMinute=" + totalCookingTimeMinute +
                ", numberOfPortions=" + numberOfPortions +
                '}';
    }
}
