package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

class Rm(name: String) extends Command {
  override def apply(state: State): State = {
    val wd: Directory = state.wd

    val absolutePath: String =
      if (name.startsWith(Directory.SEPARATOR)) name
      else {
        if (wd.isRoot)
          Directory.SEPARATOR + name
        else wd.path + Directory.SEPARATOR + name
      }

    if (absolutePath.equals(Directory.ROOT_PATH))
      state.setMessage("Can't delete root path")
    else
      doRm(state, absolutePath)
  }

  def doRm(state: State, path: String): State = {

    val tokens = path.substring(1).split("/").toList

    val newRoot: Directory = updateStructure(state.root, tokens)
    if (newRoot == state.root) state.setMessage(Command.RM + " : No such file or directory")
    val newWd = newRoot.findDescendant(tokens.init).asDirectory

    new State(newRoot, newWd, "")
  }

  def updateStructure(currentDirectory: Directory, path: List[String]): Directory = {
    if (path.size == 1) currentDirectory.removeEntry(path.head)
    else {
      val oldEntry = currentDirectory.findEntry(path.head)
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory, path.tail))
    }
  }
}
