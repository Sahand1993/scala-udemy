package exercises

abstract class MyList(head: Node = null, tail: Node = null) {
  /*
    head => (Int) first element of list
    tail => remainder of the list
    isEmpty => is this list empty?
    add(int) => new list with this element added
    toString => a string representation of your list
   */

  def isEmpty: Boolean

  def add(n: Int): MyList
}

