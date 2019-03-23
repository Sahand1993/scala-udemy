package lectures.part3fp

object HOFsCurries extends App {
  def toCurry(f: (Int, Int) => Int): Int => Int => Int = {
    (x: Int) => (y: Int) => f(x, y)
  }

  def fromCurry(f: Int => Int => Int): (Int, Int) => Int = {
    (x: Int, y: Int) => f(x)(y)
  }

  def compose(f: Int => Int, g: Int => Int): Int => Int = {
    x: Int => f(g(x))
  }

  def andThen(f: Int => Int, g: Int => Int): Int => Int = {
    compose(g, f)
  }

  val add: (Int, Int) => Int = (x: Int, y: Int) => x + y
  val res: Int = toCurry(add)(2)(3)

  val curriedAdd: Int => Int => Int = (x: Int) => (y: Int) => x + y
  println(curriedAdd(2)(3))
  val uncurriedAdd = fromCurry(curriedAdd)
  println(uncurriedAdd(2, 4))

  val double: Int => Int = (x: Int) => 2 * x
  val increment: Int => Int = (x: Int) => add(x, 1)

  println(andThen(double, increment)(1))

}
