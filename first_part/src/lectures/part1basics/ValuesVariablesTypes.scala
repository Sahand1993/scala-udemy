package lectures.part1basics

object ValuesVariablesTypes extends App{
  // vals
  val x: Int = 42
  println(x)

  // VALS ARE IMMUTABLE
  // TYPES OF VALS ARE OPTIONAL, they can be inferred by the compiler

  val aString: String = "hello"; // Semicolons are allowed but not necessary unless you write multiple expressions on the same line, but this is bad style.

  val aBoolean: Boolean = true
  val aChar: Char = 'a' // Chars have single quotes
  val anInt: Int = x
  val aShort: Short = 100
  val aLong: Long = 4123849723452345252L
  val aFloat: Float = 3.45f
  val aDouble: Double = 3.14

  // variables
  var aVariable: Int = 4
  aVariable = 5 // side effects

  // We prefer vals over vars
}
