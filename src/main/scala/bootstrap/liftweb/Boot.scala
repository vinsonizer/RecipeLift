package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import com.recipelift.model._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = new StandardDBVendor(
					 Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr 
			     "jdbc:h2:mem:recipelift.db;DB_CLOSE_DELAY=-1",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User, Recipe, Ingredient)

    // where to search snippet
		LiftRules.addToPackages("com.recipelift")

		val entries = List(
			Menu(Loc("Home", List("index"), "Home", Hidden)) ,
			Menu(Loc("Summary", List("summary"), "Summary")),
			Menu(Loc("Recipe List", List("recipeList"), "Recipe List")),
			Menu(Loc("Add Recipe", List("recipeEdit"), "Add Recipe"))
		)
		
    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
  }

	def setupCustomDispatch() = {
		LiftRules.statelessRewrite.prepend(NamedPF("ProductExampleRewrite") {
			case RewriteRequest(
					ParsePath("edit" :: id :: Nil, _, _,_), _, _) => 
				RewriteResponse(
					"recipeEdit" :: Nil, Map("id" -> id)  // Use webapp/edit.html
			)
		})
	}
}
