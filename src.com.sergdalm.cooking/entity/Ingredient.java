package entity;

public class Ingredient {
    private Integer id;
    private String name;
    private Integer weightOfOnePieceGram;
    private Integer kilocalories;
    private Double protein;
    private Double fat;
    private Double carbohydrate;

    public Ingredient() {
    }

    public Ingredient(int id, String name, Integer weightOfOnePieceGram, Integer kilocalories,
                      Double protein, Double fat, Double carbohydrate) {
        this.id = id;
        this.name = name;
        this.weightOfOnePieceGram = weightOfOnePieceGram;
        this.kilocalories = kilocalories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeightOfOnePieceGram() {
        return weightOfOnePieceGram;
    }

    public void setWeightOfOnePieceGram(Integer weightOfOnePieceGram) {
        this.weightOfOnePieceGram = weightOfOnePieceGram;
    }

    public Integer getKilocalories() {
        return kilocalories;
    }

    public void setKilocalories(Integer kilocalories) {
        this.kilocalories = kilocalories;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weightOfOnePieceGram=" + weightOfOnePieceGram +
                ", kilocalories=" + kilocalories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbohydrate=" + carbohydrate +
                '}';
    }
}
