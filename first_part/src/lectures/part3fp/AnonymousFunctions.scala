package lectures.part3fp

object AnonymousFunctions extends App {
  val doubler: Int => Int =
    x => 2 * x
  val strToInt = { str: String =>
    str.toInt
  }

  val niceIncrementer: Int => Int = _ + 1
  val niceAdder: (Int, Int) => Int = _ + _

}
