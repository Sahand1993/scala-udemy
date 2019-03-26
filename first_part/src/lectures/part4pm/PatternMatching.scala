package lectures.part4pm

import scala.util.Random

object PatternMatching extends App {
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "one and done"
    case 2 => "double or nothing"
    case 3 => "third time's the charm"
    case _ => "something else" // _ = wildcard, matches anything
  }

  println(x)
  println(description)

  // 1. Decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(name, age) if age < 21 => s"Hi, my name is $name and I can't drink in the US"
    case Person(name, age) => s"Hi, my name is $name and I am $age years old"
    case _ => "I don't know who I am"
  }
  println(greeting)

  /*
    1. cases are matched in order
    2. what if no cases match? Error will be thrown.
    3. Type of pattern match expression? The unification of all case return types
    4. PM works really well with case classes. Because they have the extractor pattern out of the box
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

  // don't use patterns on everything

  /*
    Excercise
    simple function that uses PM
    takes an Expr => human readable form

    for example:
    Sum(Number(2), Number(3)) => "2 + 2"
    Sum(Number(2), Number(3), Number(4)) => "2 + 3 + 4"
    Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
    Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3
   */
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(expr: Expr): String = expr match {
      case Number(n) => n + ""
      case Prod(e1, e2) => {
        def maybeShowParentheses(e: Expr) = e match {
          case Sum(_, _) => "(" + show(e1) + ")"
          case _ => show(e1)
        }
        maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
      }
      case Sum(e1, e2) => show(e1) + " + " + show(e2)
      case _ => ""
    }

  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4)))))
}
