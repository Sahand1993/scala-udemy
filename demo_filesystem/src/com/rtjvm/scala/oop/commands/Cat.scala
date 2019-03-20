package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.filesystem.State
import com.rtjvm.scala.oop.Constants._

class Cat(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    val file = wd.findEntry(name)
    if (file != null) {
      if (file.getType == FILE) {
        state.setMessage(file.asFile.getContents)
      } else {
        state.setMessage(s"${name} is a directory")
      }
    } else {
      state.setMessage("No such file")
    }
  }
}
