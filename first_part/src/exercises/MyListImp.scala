package exercises

class MyListImp(val head: Node = null, val tail: Node = null) extends MyList{
  def this(n: Int, next: Node) = {
    this(head = next, tail = next)
  }
  override def isEmpty = head == null

  override def add(n: Int): MyList = {
    if (head == null) return new MyListImp(n, new Node(n,null))
    val newTail = new Node(n, null)
    val out = new MyListImp(head, newTail)
    tail.add(newTail)
    out
  }

  override def toString: String = {
    def helper(currNode: Node, acc: String): String = {
      if (currNode == null) acc
      else helper(currNode.next, acc + " " + currNode.value)
    }
    helper(head, "")
  }
}
