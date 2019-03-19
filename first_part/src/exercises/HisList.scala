package exercises

abstract class HisList[+A] {

  def head: A

  def tail: HisList[A]

  def isEmpty: Boolean

  def add[T >: A](element: T): HisList[T]

  def map[B](transformer: HisTransformer[A, B]): HisList[B]

  def filter(predicate: MyPredicate[A]): HisList[A]

  def flatMap[B](transformer: HisTransformer[A, HisList[B]]): HisList[B]

  //concat
  def ++[B >: A](list: HisList[B]): HisList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"
}

case object Empty extends HisList[Nothing] {

  def head = throw new NoSuchElementException

  def tail = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[A](element: A): HisList[A] = Cons(element, Empty)

  def map[B](transformer: HisTransformer[Nothing, B]): HisList[B] = Empty

  def filter(predicate: MyPredicate[Nothing]): HisList[Nothing] = Empty

  def flatMap[B](transformer: HisTransformer[Nothing, HisList[B]]): HisList[B] = Empty

  def printElements: String = ""

  def ++[B >: Nothing](list: HisList[B]): HisList[B] = list

}

case class Cons[+A](head: A, tail: HisList[A]) extends HisList[A] {

  def isEmpty: Boolean = false

  def add[T >: A](element: T): HisList[T] = Cons(element, this)

  def printElements: String = {
    if(tail.isEmpty) "" + head
    else head + " " + tail.printElements
  }

  def map[B](transformer: HisTransformer[A, B]): HisList[B] = {
    Cons(transformer.transform(head), tail.map(transformer))
  }

  def filter(predicate: MyPredicate[A]): HisList[A] = {
    if (predicate.test(head)) Cons(head, tail.filter(predicate))
    else tail.filter(predicate)
  }

  def ++[B >: A](list: HisList[B]): HisList[B] = Cons(head, tail ++ list)

  def flatMap[B](transformer: HisTransformer[A, HisList[B]]): HisList[B] = Cons(transformer.transform(head).head, transformer.transform(head).tail ++ tail.flatMap(transformer)) // Or just transformer.transform(head) ++ tail.flatMap(transformer) (more efficient)
}

trait MyPredicate[-A] {
  def test(subject: A): Boolean
}

class StringToIntTransformer extends HisTransformer[String, Int] {
  def transform(subject: String): Int = subject.toInt
}

class DoubleTransformer extends HisTransformer[Int, Int] {
  def transform(subject: Int): Int = subject * 2
}

class DoubleFlatTransformer extends HisTransformer[Int, HisList[Int]]{
  def transform(subject: Int): HisList[Int] = new Cons(subject, new Cons(subject, Empty))
}

object ListTest extends App {
  val list = new Cons("1", new Cons("2", new Cons("3", new Cons("4", Empty))))
  val ints: HisList[Int] = list.map(new StringToIntTransformer)
  println(ints)

  println(ints.filter(new MyPredicate[Int]() {
    def test(subject: Int): Boolean = subject % 2 == 0
  }))

  println(ints ++ ints)

  println(ints.flatMap(new HisTransformer[Int, HisList[Int]] {
    def transform(subject: Int): HisList[Int] = new Cons(subject, new Cons(subject + 1, Empty))
  }))


  //println(ints.flatMap(new DoubleFlatTransformer))
}

trait HisTransformer[-A, B]{
  def transform(subject: A): B
}