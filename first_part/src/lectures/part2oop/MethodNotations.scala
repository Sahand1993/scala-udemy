package lectures.part2oop

object MethodNotations extends App {

  class Person(val name: String, val age: Int = 0, favoriteMovie: String){
    def likes(movie: String) = movie == favoriteMovie
    def +(person: Person) = s"${this.name} is hanging out with ${person.name}"
    def +(str: String): Person = new Person(s"$name ($str)", favoriteMovie = favoriteMovie)
    def unary_! : String = s"$name, what the heck?!"
    def unary_+ : Person = new Person(name, age + 1, favoriteMovie)
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
    def apply(n: Int): String = s"$name watched $favoriteMovie $n times"
    def learns(str: String): String = s"$name learns $str"
    def learnsScala: String = this learns "Scala"
  }

  val mary = new Person("Mary", favoriteMovie = "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception") // equivalent
  // the above is called infix notation / operator notation. Only works with method that only have a single parameter. Syntex: object method parameter

  // "operators" in Scala
  val tom = new Person("Tom", favoriteMovie = "Fight Club")
  println(mary + tom)
  println(mary.+(tom))

  println(1 + 2)
  println(1.+(2)) // equivalent

  // ALL OPERATORS ARE METHODS

  // prefix notation
  val x = -1 // equivalent with 1.unary_-
  val y = 1.unary_-
  // unary_ prefix only works with a few operators: - + ~ !
  println(!mary)
  println(mary.unary_!)

  // postfix notation
  println(mary.isAlive)
  println(mary isAlive)

  // apply
  println(mary.apply())
  println(mary()) // equivalent

  println((mary + "the rockstar").apply())
  println((+mary).age)

  println(mary learnsScala)

  println(mary(2))
}
