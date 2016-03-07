package reactive

import spray.json.DefaultJsonProtocol

case class Die(sides: Int)
case class Roll(die: Die, result: Int)

object RollProtocol extends DefaultJsonProtocol {
  implicit val fmtDie = jsonFormat1(Die.apply)
	implicit val fmtRoll = jsonFormat2(Roll.apply)
}
