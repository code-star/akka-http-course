package reactive

//import spray.json.DefaultJsonProtocol

case class Die(sides: Int)
case class Roll(die: Die, result: Int)
