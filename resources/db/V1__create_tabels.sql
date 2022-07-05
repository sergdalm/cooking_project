CREATE DATABASE cooking_project;

CREATE SCHEMA IF NOT EXISTS cooking;

SET search_path TO cooking, public;

-- пользователь
-- (слово user уже зарезервировано, поэтому таблица названа users)
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128)        NOT NULL,
    is_admin BOOLEAN             NOT NULL
    );

-- детали пользователя
CREATE TABLE IF NOT EXISTS user_details
(
    user_id    INTEGER REFERENCES users (id) PRIMARY KEY,
    username   VARCHAR(128) UNIQUE NOT NULL,
    first_name VARCHAR(128)        NOT NULL,
    last_name  VARCHAR(128)        NOT NULL,
    birthday   TIMESTAMP           NOT NULL
    );

-- ингредиенты
CREATE TABLE IF NOT EXISTS ingredient
(
    id                       SERIAL PRIMARY KEY,
    name                     VARCHAR(128) UNIQUE NOT NULL,
    weight_of_one_piece_gram INTEGER,
    kilocalories             INTEGER             NOT NULL,
    protein_mg               INTEGER             NOT NULL,
    fat_mg                   INTEGER             NOT NULL,
    carbohydrate_mg          INTEGER             NOT NULL
    );

-- приправы
CREATE TABLE IF NOT EXISTS spices
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) UNIQUE NOT NULL
    );

-- рецепты
CREATE TABLE IF NOT EXISTS recipe
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) UNIQUE NOT NULL
    );

-- детали рецепта
-- (время приготовление разделено на активное (сама готовка)
-- и общее (включая время на запекание, маринад и т.п.))
-- user_id - id пользователя, создавшего рецепт
CREATE TABLE IF NOT EXISTS recipe_detail
(
    recipe_id                  INTEGER REFERENCES recipe (id) PRIMARY KEY,
    user_id                    INTEGER REFERENCES users (id),
    description                TEXT NOT NULL,
    active_cooking_time_minute INTEGER,
    total_cooking_time_minute  INTEGER
    );

-- соединение рецепт + ингредиент
CREATE TABLE IF NOT EXISTS recipe_ingredient
(
    recipe_id     INTEGER REFERENCES recipe (id),
    ingredient_id INTEGER REFERENCES ingredient (id),
    gram          INTEGER,
    notes         VARCHAR(128),
    PRIMARY KEY (recipe_id, ingredient_id)
    );

-- соединение: рецепт + приправа
CREATE TABLE IF NOT EXISTS recipe_spices
(
    recipe_id INTEGER REFERENCES recipe (id),
    spicy_id  INTEGER REFERENCES spices (id),
    notes     VARCHAR(128),
    PRIMARY KEY (recipe_id, spicy_id)
    );


-- соединение: юзер + рецепт (избранные рецепты)
CREATE TABLE IF NOT EXISTS user_favorite_recipes
(
    user_id   INTEGER REFERENCES users (id),
    recipe_id INTEGER REFERENCES recipe (id),
    PRIMARY KEY (user_id, recipe_id)
    );

-- план питания
CREATE TABLE IF NOT EXISTS meal_plan
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(128) NOT NULL,
    user_id INTEGER REFERENCES users (id),
    UNIQUE (user_id, name)
    );

-- один день в плане питания
CREATE TABLE IF NOT EXISTS meal_plan_day
(
    id           SERIAL PRIMARY KEY,
    meal_plan_id INTEGER REFERENCES meal_plan (id),
    day_number   INTEGER NOT NULL,
    UNIQUE (meal_plan_id, day_number)
    );

-- связь дня плана питания с рецептом
CREATE TABLE IF NOT EXISTS meal_plan_recipe
(
    meal_plan_day_id INTEGER REFERENCES meal_plan_day (id),
    recipe_id        INTEGER REFERENCES recipe (id),
    meal_type        VARCHAR(64) NOT NULL,
    PRIMARY KEY (meal_plan_day_id, meal_type)
    );