package lectures.part2oop

object Inheritance extends App{

  // Scala offers single-class inheritance
  sealed class Animal {
    val creatureType = "wild"
    def eat = println("noomnomonom")
  }

  class Cat extends Animal {
    def crunch = {
      eat
      println("crunchcrunch")
    }
  }

  val cat = new Cat
  cat.crunch

  // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String) extends Person(name) // parameters to Person constructor (which has to be called in the JVM)

  class Dog (override val creatureType: String) extends Animal {
    //override val creatureType = "domestic"
    override def eat = {
      super.eat
      println("crunch, crunch")
    }
  }

/*  class Dog(dogType: String) extends Animal {
    override val creatureType: String = dogType
  }*/
  val dog = new Dog("Wild af")
  dog.eat
  println(dog.creatureType)


  // type substitution (broad: polymorphism)
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat

  // super


  // how to prevent overrides:
  // 1 - use final
  // 2 - use final on entire class
  // 3 - seal the class = extend classes in THIS FILE, but prevent extension in other files
}
