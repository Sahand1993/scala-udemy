package lectures.part2oop

object AbstractDataTypes extends App{
  // abstract
  abstract class Animal {
    val creatureType: String = "wild"
    def eat: Unit
  }

  class Dog extends Animal{
    override val creatureType: String = "Canine"
    override def eat: Unit = println("crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "Croc"

    override def eat: Unit = println("nononomonom")
    override def eat(animal: Animal): Unit = println(s"I'm a ${creatureType} and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // traits vs abstract classes
  // 1. traits can not have constructor parameters
  // 2. You can only extend one class but you can mix in multiple traits.
  // 3. traits are behaviours. abstract classes are things

}
