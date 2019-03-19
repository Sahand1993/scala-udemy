package playground

import scala.annotation.tailrec

object ScalaPlayground extends App{

  def collapseRelativeTokens(path: List[String]): List[String] = {
    val withoutDot = path.filter(e => !e.equals("."))
    val withoutDoubleDot = collapseParentPaths(withoutDot)
    return withoutDoubleDot
  }

  def collapseParentPaths(path: List[String]): List[String] = {
    @tailrec
    def helper(reversedPathLeft: List[String], resultingPath: List[String]): List[String] = {
      if (reversedPathLeft.isEmpty) resultingPath.reverse
      else if (reversedPathLeft.head.equals("..")) helper(reversedPathLeft.slice(2, reversedPathLeft.size), resultingPath)
      else helper(reversedPathLeft.tail, resultingPath :+ reversedPathLeft.head)
    }
    helper(path.reverse, List())
  }

  println(collapseRelativeTokens(List("a", "..", "b", "c", ".", "b", "..")))

}
