package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.Directory
import com.rtjvm.scala.oop.filesystem.{DirectoryNotPresentException, State}

import scala.annotation.tailrec

class Cd(path: List[String]) extends Command {
  override def apply(state: State): State = {
    try {
      State(state.root, getNewWd(state, path))
    } catch {
      case e: DirectoryNotPresentException => state.setMessage("Directory not present")
    }
  }

  @tailrec
  private def getNewWd(state: State, path: List[String]): Directory = {
    if (isAbsolutePath(path)) {
      getNewWd(new State(state.root, state.root, state.output), path.tail)
    }
    else if (path.size == 1) {
      if (path(0) == ".") state.wd
      else if (path(0) == "..") state.root.findDescendant(state.wd.parentPath.split(Directory.SEPARATOR).filter(e => !e.isEmpty).toList).asDirectory
      else {
        val entry = state.wd.findEntry(path(0))
        if (entry != null) {
          entry.asDirectory
        } else throw new DirectoryNotPresentException
      }
    }
    else {
      getNewWd(state.moveWd(path.head), path.tail)
    }
  }

  def isAbsolutePath(path: List[String]): Boolean = path.head.equals("")

}