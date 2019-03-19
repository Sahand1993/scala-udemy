package lectures.part2oop

object OOBasics extends App{
  val person = new Person("John", 26)
  println(person.x)
  person.greet("Daniel")
  person.greet

  val author = new Writer("Charles", "Dickens", 1812)
  val impostor = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, impostor)

  println(novel.authorAge())
  println(novel.isWrittenBy(author))

  val counter = new Counter()
  counter.increment.print
  counter.increment.increment.increment.print
  counter.increment(10).print
}

class Person(name: String, val age: Int) { // constructor
  // body
  val x = 2
  println(1 + 3)

  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  // overloading
  def greet(): Unit = println(s"Hi, I am $name")

  // overloading constructors
  def this(name: String) = this(name, 0) // We don't need this, it only results in a default parameter
  def this() = this("John Doe")
}

/*
  Novel and a Writer

  Writer: first name, surname, year of birth
    - method fullname

  Novel: name, year of release, author
    - authorAge
    - isWrittenBy(author)
    - copy (new year of release) = new instance of Novel
 */

class Writer(firstName: String, surname: String, val birthYear: Int){
  def fullName(): String = s"$firstName $surname"
}

class Novel(name: String, year: Int, author: Writer){
  def authorAge(): Int = year - author.birthYear

  def isWrittenBy(author: Writer): Boolean = this.author == author

  def copy(newYearOfRelease: Int): Novel = new Novel(name, newYearOfRelease, author)
}



class Counter(count: Int = 0){

  def currentCount = count

  def increment = {
    println("incrementing")
    new Counter(count + 1) // immutability
  }

  def increment(amount: Int): Counter = {
    if (amount <= 0) this
    else increment.increment(amount - 1)
  }

  def decrement = {
    println("decrementing")
    new Counter(count - 1)
  }

  def decrement(amount: Int): Counter = {
    if (amount <= 0) this
    else decrement.decrement(amount - 1)
  }

  def print = println(currentCount)
}

// class parameters are NOT FIELDS