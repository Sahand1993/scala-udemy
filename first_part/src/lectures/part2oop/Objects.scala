package lectures.part2oop

object Objects extends App{
  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")
  // Instead, it has objects (even better)

  object Person { // Objects do NOT receive parameters. // here we define type + only instance of Person
    // "static"/"class"-level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method - looks like Java-style constructor when used.
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }
  class Person(val name: String) {
    // instance-level functionality
  }
  // the object and class are COMPANIONS

  println(Person.N_EYES)
  println(Person.canFly)

  // Scala object = SINGLETON INSTANCE

  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john)

  val bobbie = Person(mary, john)

  // Scala Applications = Scala object with
  // def main(args: Array[String]): Unit (This is equivalent to the java main method)
}
