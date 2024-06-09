CREATE TABLE Ingredient(
    id IDENTITY NOT NULL PRIMARY KEY,

);
INSERT INTO recipe(id, name, description, difficulty) VALUES (1, 'Cake with shit', 'Mmmm yammy!', 'EASY');
INSERT INTO Ingredient(id, name, type) VALUES(1,'Milk','LITERS');