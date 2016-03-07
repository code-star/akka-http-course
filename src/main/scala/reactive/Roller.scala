package reactive

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

object Roller {
  def roll(die: Die): Future[Roll] = Future.successful(Roll(die, 1 + (Math.random() * die.sides).toInt))
  def rollSlow(die: Die)(implicit ex: ExecutionContext): Future[Roll] =
    Future {
      Thread.sleep((Math.random() * 1500).toLong)
      Roll(die, 1 + (Math.random() * die.sides).toInt)
    }
}