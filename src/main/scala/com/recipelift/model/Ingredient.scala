package com.recipelift.model

import _root_.net.liftweb.mapper._
import _root_.java.math.MathContext

class Ingredient extends LongKeyedMapper[Ingredient] with IdPK {
	def getSingleton = Ingredient
	
	object amount extends MappedDecimal(this,MathContext.DECIMAL64, 2)
	object unit extends  MappedString(this, 20)
	object name extends MappedString(this, 100)
	object modifier extends MappedString(this, 200)
}

object Ingredient extends Ingredient with LongKeyedMetaMapper[Ingredient] {
	override def fieldOrder = List(amount, unit, name, modifier)
}