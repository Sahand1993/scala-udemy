package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  val MKDIR: String = "mkdir"
  val LS: String = "ls"
  val PWD: String = "pwd"
  val TOUCH: String = "touch"
  val CD: String = "cd"
  val RM: String = "rm"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = {
      State.apply(state.root, state.wd)
    }
  }

  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State = {
      state.setMessage(name + ": incomplete command")
    }
  }

  def tooManyArguments(name: String): Command = new Command {
    override def apply(state: State): State = {
      state.setMessage(name + ": too many arguments")
    }
  }

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")

    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens.head)) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    }
    else if (LS.equals(tokens.head)) {
      if (tokens.length > 1) tooManyArguments(LS)
      else new Ls
    }
    else if (PWD.equals(tokens.head)) {
      if (tokens.length > 1) tooManyArguments(PWD)
      else new Pwd
    }
    else if (TOUCH.equals(tokens.head)) {
      if (tokens.length < 2) incompleteCommand(TOUCH)
      else new Touch(tokens(1))
    }
    else if (CD.equals(tokens.head)) {
      if (tokens.length < 2) incompleteCommand(CD)
      else new Cd(tokens(1).split("/").toList)
    }
    else if (RM.equals(tokens.head)) {
      if (tokens.length < 2) incompleteCommand(RM)
      else new Rm(tokens(1))
    }
    else new UnknownCommand
  }
}