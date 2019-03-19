package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State
import com.rtjvm.scala.oop.Constants._

class Ls extends Command {
  override def apply(state: State): State = {
    val contentsString: String = getContentsString(state.wd)
    state.setMessage(contentsString)
  }

  def getContentsString(dir: Directory): String = {
    def helper(acc: String, entriesLeft: List[DirEntry]): String = {
      if (entriesLeft.isEmpty) acc
      else {
        val formattedEntryString: String = if (entriesLeft.head.getType.equals(DIR)) entriesLeft.head.name + "/"
                                          else entriesLeft.head.name
        helper(acc + " " + formattedEntryString, entriesLeft.tail)
      }
    }
    val contentsString = helper("", dir.contents)
    if (!contentsString.isEmpty) contentsString.substring(1) else ""
  }
}
