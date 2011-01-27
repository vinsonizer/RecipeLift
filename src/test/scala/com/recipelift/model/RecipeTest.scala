package com.recipelift.model

import net.liftweb.mapper._
import java.util.Date

class RecipeTest extends DBSpecTest {  

	"Recipe" should {
		"support simple save and delete in the database" in {
			var recipe = Recipe.create.name("Whole Wheat Bread")
				.activeTime(.5)
				.totalTime(5)
				.dateAdded(new Date())
				.directions("Put the Jelly in the bowl")
				.saveMe
			Recipe.count must_== 1
			recipe.delete_!
			Recipe.count must_== 0
		}
		
		"allow to retrieve by id" in {
			val recipeId = Recipe.create.name("something").saveMe.id.is
			val fromDb = Recipe.findAll(By(Recipe.id, recipeId))
			fromDb.size must_== 1
			fromDb.first.id.is must_== recipeId
		}
		
		"allow to retrieve by name" in {
			val toast = "toast"
			val recipe = Recipe.create.name(toast).saveMe
			val fromDb = Recipe.findAll(By(Recipe.name, toast))
			fromDb.size must_== 1
			fromDb.first.name.is must_== toast
			fromDb.first.id must_== recipe.id
		}
		
		"allow retrieval of ingredients" in {
			val coldCereal = "cold cereal"
			val milk = "milk"
			val flakes = "flakes"
			val recipe = Recipe.create.name(coldCereal).saveMe
			Ingredient.create.name(milk).recipe(recipe).saveMe
			Ingredient.create.name(flakes).recipe(recipe).saveMe
			val fromDb = Recipe.findAll(By(Recipe.name, coldCereal))
			fromDb.size must_== 1
			fromDb.first.name.is must_== coldCereal
			fromDb.first.id must_== recipe.id
			fromDb.first.ingredients.size must_== 2
		}
	}
} 