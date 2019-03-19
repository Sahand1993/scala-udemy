package lectures.part1basics

object Expressions extends App{
  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4)
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x)
  // == != < > >= <=

  println(!(1 == x))
  // ! && ||

  var aVariable = 2
  aVariable += 3 // also works with -= *= and /= ....... side effects
  println(aVariable)

  // Instructions (DO) vs Expressions (VALUE)

  // IF expression
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3 // IF EXPRESSION (not if instruction)
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)
  println(1 + 3)

  var i = 0

  val aWhile = while (i < 10){
    println(i)
    i += 1
  }

  // NEVER WRITE THIS AGAIN. AVOID WHILE LOOPS LIKE THE PLAGUE.

  // EVERYTHING in Scala is an Expression!

  val aWeirdValue = (aVariable = 3) // Unit == void
  println(aWeirdValue)

  // side effects: println(), whiles, reassigning. These are expressions returning unit

  // Code blocks

  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  }

  val someValue = {
    2 < 3
  }
  println(if(someValue == true) "bullshit" else "trueshit")

  val someOtherValue = {
    if(someValue) 239 else 986
    42
  }
  println(if(someOtherValue == 42) "I was right" else "I was wrong")

}
