package com.rtjvm.scala.oop.filesystem

import com.rtjvm.scala.oop.files.Directory
import com.rtjvm.scala.oop.Constants._

class State(val root: Directory, val wd: Directory, val output: String) {

  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }
  def setMessage(message: String): State =
    State(root, wd, message)

  def moveWd(to: String): State = {
    /*

    /a => parentPath "/" => []
    /a/b => parentPath "/a" => ["", "a"]
    / => parentPath "" => [""]
     */

    if (to.equals(PARENT_PATH)) {
      if (wd.parentPath.equals(Directory.ROOT_PATH)) new State(root, root, "")
      else if (wd.isRoot) this.setMessage("Directory not present")
      else {
        val parentPath: List[String] = wd.parentPath.split(Directory.SEPARATOR).toList.tail
        new State(root, root.findDescendant(parentPath).asDirectory, "")
      }

    } else if (to.equals(CURRENT_DIRECTORY)) this
    else State(root, wd.findEntry(to).asDirectory, "")
  }

}

object State {

  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State =
    new State(root, wd, output)

}