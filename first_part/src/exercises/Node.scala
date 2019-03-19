package exercises

protected class Node(val value: Int, var next: Node) {
  def add(newTail: Node): Unit = {
    def addHelper(next: Node, curr: Node): Unit = {
      if (next == null) curr.next = newTail
      else addHelper(next.next, curr)
    }
    addHelper(this.next, this)
  }
}