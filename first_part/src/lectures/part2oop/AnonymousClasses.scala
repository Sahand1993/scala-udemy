package lectures.part2oop

object AnonymousClasses extends App{
  abstract class Animal {
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("ahahaha")
  }

  println(funnyAnimal.getClass)

  class Person(name: String){
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }

  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim, how can I be of service?")
  }

  /*
    1. Create a generic trait called MyPredicate[T] that has one method that tests whether T has a condition?
    2. Generic trait MyTransformer[A, B]
   */
}
