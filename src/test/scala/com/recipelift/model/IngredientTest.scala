package com.recipelift.model

class IngredientTest extends DBtest {  

	"Ingredient" should {
		"save and delete in the database" in {
			var ingredient = Ingredient.create.amount(1).unit("cup").name("pecans").modifier("crushed").saveMe
			Ingredient.count must_== 1
			ingredient.delete_!
			Ingredient.count must_== 0
		}
	}
} 