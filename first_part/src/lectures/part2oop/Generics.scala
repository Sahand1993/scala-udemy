package lectures.part2oop

object Generics extends App{
  class MyList[+A] {
    // use the type A
    def add[B >: A](element: B): MyList[B] = ???
  }
  class MyMap[Key, Value]
  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ???
  }
  //val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // Does a list of Cats extend a List of Animals?
  // 1. Yes (covariance)
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
    // would I be able to add a Dog element to animalList? It's okay for the left hand side, but not for the right hand... Answer: We return a list of animals

  // 2. No (Invariance)
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal] //has to be same type

  // 3. Hell, no! (Contravariance)
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // bounded types
  class Cage[A <: Animal] (animal: A) // >: only allows supertypes
  val cage = new Cage(new Dog)

  class Car
  val newCage = new Cage(new Animal)


  // expand MyList to be generic
}
