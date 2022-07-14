package com.sergdalm.cooking.entity;

import com.sergdalm.cooking.util.ValueSetterUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ingredient {
    private Integer id;
    private String name;
    private Integer weightOfOnePieceGram;
    private Integer kilocalories;
    private Double protein;
    private Double fat;
    private Double carbohydrate;
    @Getter
    private static final String ID = "id";
    @Getter
    private static final String NAME = "name";
    @Getter
    private static final String WEIGHT_OF_ONE_PIECE_GRAM = "weight_of_one_piece_gram";
    @Getter
    private static final String KILOCALORIES = "kilocalories";
    @Getter
    private static final String PROTEIN = "protein_gr";
    @Getter
    private static final String FAT = "fat_gr";
    @Getter
    private static final String CARBOHYDRATE = "carbohydrate_gr";


    public static Ingredient build(ResultSet resultSet) {
        try {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(resultSet.getInt(ID));
            ingredient.setName(resultSet.getString(NAME));
            ValueSetterUtil.setEntityValueIfNotNull(ingredient, Ingredient::setWeightOfOnePieceGram, resultSet, WEIGHT_OF_ONE_PIECE_GRAM, Integer.class);
            ingredient.setKilocalories(resultSet.getInt(KILOCALORIES));
            ingredient.setProtein(resultSet.getDouble(PROTEIN));
            ingredient.setFat(resultSet.getDouble(FAT));
            ingredient.setCarbohydrate(resultSet.getDouble(CARBOHYDRATE));
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
