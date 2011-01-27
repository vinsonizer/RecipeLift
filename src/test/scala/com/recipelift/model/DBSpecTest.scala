package com.recipelift.model

import org.specs._  
import org.specs.matcher._  
import net.liftweb.mapper._
import net.liftweb.common._  
import net.liftweb.util._  

import java.sql._  
  
class DBSpecTest extends Specification {  
	new SpecContext {
		// clean the DB
		beforeExample(DBSpecTest.init)
	}
} 

object DBSpecTest extends Specification {
	val vendor = new StandardDBVendor(
		"org.h2.Driver",   
    "jdbc:h2:mem:testDb;DB_CLOSE_DELAY=-1",  
		Empty, 
		Empty
	)  
	
	Logger.setup = Full(net.liftweb.util.LoggingAutoConfigurer())  
	Logger.setup.foreach { _.apply() }  
		
	def init {
		DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)  
		Schemifier.destroyTables_!!(Schemifier.infoF _, User, Recipe, Ingredient)  
		Schemifier.schemify(true, Schemifier.infoF _, User, Recipe, Ingredient)  
	}
 
}