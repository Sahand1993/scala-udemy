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
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

}
