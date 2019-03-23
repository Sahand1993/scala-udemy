package lectures.part3fp

object WhatsAFunction extends App {
  // Use functions as first class elements
  // Ie work with functions as with values
  // Problem: We come from an object-oriented world.
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  val tripler: Int => Int = _ * 3

  val newTripler: Int => Int = _ * 3

  val concatStrings: (String, String) => String = _ + _

  println(concatStrings(concatStrings("Hi", " "), "again"))

  val superMultiply: Int => Function1[Int, Int] = (x: Int) => (y: Int) => x * y

  val superAdd =  (x: Int) => (y: Int) => x + y

  val multiply3 = superMultiply(3)

  println(multiply3(10))
  println(superMultiply(2)(10)) // curried function

  println(superAdd(2)(4))


}

trait MyFunction[A, B] {
  def apply(element: A): B

}