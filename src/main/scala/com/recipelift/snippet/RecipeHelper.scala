package com.recipelift {
	package snippet {

		import _root_.scala.xml.{NodeSeq, Text}
		import _root_.net.liftweb.util._
		import _root_.net.liftweb.http._
		import _root_.net.liftweb.common._
		import _root_.java.util.Date
		import com.recipelift.model._
		import Helpers._

		class RecipeHelper {
			
			object id extends RequestVar("")
			object name extends RequestVar("")
			object serves extends RequestVar("0")
			object activeTime extends RequestVar("0")
			object totalTime extends RequestVar("0")
			object directions extends RequestVar("")
			
			def addOrUpdate(xhtml: NodeSeq) : NodeSeq = {
				
				
				def processEntryAdd () =  { 
					Recipe.create.name(name.is)
					.dateAdded(new Date())
					.directions(directions.is)
					.saveMe
					
					S.redirectTo("recipeList")
				}
				
				def textField(variable: RequestVar[String], id: String) = {
					SHtml.text(variable.is, variable(_), "id" -> id)
				}
				
				bind("entry", xhtml,
				  "id"              -%> SHtml.hidden(() => println("submitted for id is " + id.is)),
					"name"         -%> textField(name, "name"),
					"serves"       -%> textField(serves, "serves"),
					"activeTime" -%> textField(activeTime, "activeTime"),
					"totalTime"   -%> textField(totalTime, "totalTime"),
					"directions"  -%> SHtml.textarea(
						directions.is, 
						directions(_), 
						"cols" -> "80", 
						"rows" -> "8"),
					"submit" -> SHtml.submit("Add", processEntryAdd))
			}
			
			def list(xhtml: NodeSeq): NodeSeq = {
				val recipes = Recipe.findAll
				<div>
				  <ul>
				  {for (recipe <- recipes)  yield
							//<li><a href="#">{recipe.name}</a></li>
						  <li>{ SHtml.link("recipeAdd", () => println("clicked"), <i>{recipe.name}</i>) }</li>
					}
					</ul>
			  </div>
			}
			
		}
	}
}
