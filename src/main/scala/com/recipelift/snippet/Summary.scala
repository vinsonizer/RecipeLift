package com.recipelift {
	package snippet {

		import _root_.scala.xml.{NodeSeq, Text}
		import _root_.net.liftweb.util._
		import _root_.net.liftweb.common._
		import _root_.java.util.Date
		import code.lib._
		import Helpers._

		class Summary {
			def topRecipes (xhtml : NodeSeq) : NodeSeq = {
				 <ul>
				   <li>one</li>
					 <li>two</li>
				 </ul>
			}
		}
	}
}
