package com.recipelift.model

import _root_.net.liftweb.mapper._
import _root_.java.math.MathContext

class Recipe extends LongKeyedMapper[Recipe] with IdPK {
	def getSingleton = Recipe
	
	object name extends MappedString(this, 100) {
		override def dbIndexed_? = true
	}
	object serves extends MappedInt(this)
	object activeTime extends MappedDecimal(this,MathContext.DECIMAL64, 2)
	object totalTime extends MappedDecimal(this,MathContext.DECIMAL64, 2)
	object dateAdded extends MappedDate(this)
	object directions extends MappedText(this)
	
	def ingredients = Ingredient.findAll(By(Ingredient.recipe, this.id))
}

object Recipe extends Recipe with LongKeyedMetaMapper[Recipe] {
	override def fieldOrder = List(
		name, serves, activeTime, totalTime, dateAdded, directions
	)
}