package com.recipelift.model

import net.liftweb.mapper._

class IngredientTest extends DBSpecTest {  

	"Ingredient" should {
		"support simple save and delete in the database" in {
			var ingredient = Ingredient.create.amount(.5).unit("cup").name("pecans").modifier("crushed").saveMe
			Ingredient.count must_== 1
			ingredient.delete_!
			Ingredient.count must_== 0
		}
		
		"allow to retrieve by id" in {
			val ingredientId = Ingredient.create.amount(.5).unit("tbsp").name("salt").saveMe.id.is
			val fromDb = Ingredient.findAll(By(Ingredient.id, ingredientId))
			fromDb.size must_== 1
			fromDb.first.id.is must_== ingredientId
		}
		
		"allow to retrieve by name" in {
			val choc = "chocolate chips"
			val ingredient = Ingredient.create.amount(1).unit("ounce").name(choc).saveMe
			val fromDb = Ingredient.findAll(By(Ingredient.name, choc))
			fromDb.size must_== 1
			fromDb.first.name.is must_== choc
			fromDb.first.id must_== ingredient.id
		}
	}
} 