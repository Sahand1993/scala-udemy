package exercises

abstract class HisList[+A] {

  def head: A

  def tail: HisList[A]

  def isEmpty: Boolean

  def add[T >: A](element: T): HisList[T]

  def map[B](transformer: A => B): HisList[B]

  def filter(predicate: A => Boolean): HisList[A]

  def flatMap[B](transformer: A => HisList[B]): HisList[B]

  //concat
  def ++[B >: A](list: HisList[B]): HisList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"

  def forEach(action: A => Unit): Unit

  def sortOnce(comparator: (A, A) => Int): HisList[A]

  def isSorted(compare: (A, A) => Int): Boolean

  def sort(compare: (A, A) => Int): HisList[A]


  def zipWith[B, C](otherList: HisList[B], f: (A, B) => C): HisList[C]

  def fold[B](startValue: B): ((A, A) => B) => B

}

case object Empty extends HisList[Nothing] {

  def head = throw new NoSuchElementException

  def tail = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[A](element: A): HisList[A] = Cons(element, Empty)

  def map[B](transformer: Nothing => B): HisList[B] = Empty

  def filter(predicate: Nothing => Boolean): HisList[Nothing] = Empty

  def flatMap[B](transformer: Nothing => HisList[B]): HisList[B] = Empty

  def printElements: String = ""

  def ++[B >: Nothing](list: HisList[B]): HisList[B] = list

  override def forEach(action: Nothing => Unit): Unit = ()

  override def sortOnce(compare: (Nothing, Nothing) => Int): HisList[Nothing] = Empty

  override def isSorted(compare: (Nothing, Nothing) => Int): Boolean = true

  override def sort(compare: (Nothing, Nothing) => Int): HisList[Nothing] = Empty

  override def zipWith[B, C](otherList: HisList[B], f: (Nothing, B) => C): HisList[C] = {
    if (!otherList.isEmpty) throw new RuntimeException("Lists had different lengths")
    else Empty
  }

  override def fold[B](startValue: B): ((Nothing, Nothing) => B) => B = ???

}

case class Cons[+A](head: A, tail: HisList[A]) extends HisList[A] {

  def isEmpty: Boolean = false

  def add[T >: A](element: T): HisList[T] = Cons(element, this)

  def printElements: String = {
    if(tail.isEmpty) "" + head
    else head + " " + tail.printElements
  }

  def map[B](transformer: A => B): HisList[B] = {
    Cons(transformer(head), tail.map(transformer))
  }

  def filter(predicate: A => Boolean): HisList[A] = {
    if (predicate(head)) Cons(head, tail.filter(predicate))
    else tail.filter(predicate)
  }

  def ++[B >: A](list: HisList[B]): HisList[B] = Cons(head, tail ++ list)

  def flatMap[B](transformer: A => HisList[B]): HisList[B] = Cons(transformer(head).head, transformer(head).tail ++ tail.flatMap(transformer)) // Or just transformer.transform(head) ++ tail.flatMap(transformer) (more efficient)

  override def forEach(action: A  => Unit): Unit = {
    action(head)
    tail.forEach(action)
  }

  override def sortOnce(compare: (A, A) => Int): HisList[A] = {
    /* c, a, d, b
       a, c, d, b
       a, c, d, b
       a, c, b, d
    */
    val diff = compare(head, tail.head)
    if (diff <= 0) new Cons[A](head, tail.sortOnce(compare))
    else {
      new Cons[A](tail.head, tail.tail.add(head))
    }
  }

  def isSorted(compare: (A, A) => Int): Boolean = {
    if(tail.isEmpty)
      true
    else
      compare(head, tail.head) <= 0 && tail.isSorted(compare)
  }

  override def sort(compare: (A, A) => Int): HisList[A] = {
    if (!isSorted(compare)) {
      sortOnce(compare).sort(compare)
    }
    else {
      this
    }
  }

  override def zipWith[B, C](otherList: HisList[B], f: (A, B) => C): HisList[C] = {
    if (otherList.isEmpty) throw new RuntimeException("Lists had different length")
    else new Cons(f(head, otherList.head), tail.zipWith(otherList.tail, f))
  }

  override def fold(startValue: A): ((A, A) => A) => A = {
    def helper(f: (A, A) => A): A = {
      def inner(accum: A, restOfList: HisList[A]): A = {
        inner(f(accum, restOfList.head), restOfList.tail)
      }
      inner(startValue, this)
    }
    helper
  }

}

//
//class StringToIntTransformer extends HisTransformer[String, Int] {
//  def transform(subject: String): Int = subject.toInt
//}
//
//class DoubleTransformer extends HisTransformer[Int, Int] {
//  def transform(subject: Int): Int = subject * 2
//}
//
//class DoubleFlatTransformer extends HisTransformer[Int, HisList[Int]]{
//  def transform(subject: Int): HisList[Int] = new Cons(subject, new Cons(subject, Empty))
//}

object ListTest extends App {
  val list1 = new Cons(-1, new Cons(4, new Cons(3, new Cons(-11, Empty))))
  val list2 = new Cons(-1, new Cons(4, new Cons(3, new Cons(-11, Empty))))

  list1.zipWith(list2, (x: Int, y: Int) => x + y).forEach(println)
 //  println(list.fold(0, (x: Int, y: Int) => x + y))
}
