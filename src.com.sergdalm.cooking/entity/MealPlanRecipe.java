package entity;

public class MealPlanRecipe {
    private Integer id;
    private Integer mealPlanId;
    private Integer recipeId;
    private Integer dayNumber;
    private MealType mealType;

    public MealPlanRecipe() {

    }

    public MealPlanRecipe(Integer id, Integer mealPlanId, Integer recipeId, Integer dayNumber, MealType mealType) {
        this.id = id;
        this.mealPlanId = mealPlanId;
        this.recipeId = recipeId;
        this.dayNumber = dayNumber;
        this.mealType = mealType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(Integer mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    @Override
    public String toString() {
        return "MealPlanRecipe{" +
                "id=" + id +
                ", mealPlanId=" + mealPlanId +
                ", recipeId=" + recipeId +
                ", dayNumber=" + dayNumber +
                ", mealType=" + mealType +
                '}';
    }
}
