package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.filesystem.State
import com.rtjvm.scala.oop.Constants._
import com.rtjvm.scala.oop.files.{Directory, File}

class Echo(args: Array[String]) extends Command {


  override def apply(state: State): State = {
    if (args.size == 0) state
    else if (args.size == 1) state.setMessage(args(0))
    else {
      val newContent: String = args.slice(0, args.size - 2).mkString
      val operator = args.init.last
      val filePath: String = args.last
      val absolutePath =
        if (filePath.startsWith(Directory.SEPARATOR)) {
          filePath
        } else {
          if (state.wd.isRoot)
            state.wd.path + filePath
          else
            state.wd.path + Directory.SEPARATOR + filePath
        }
      echoToFile(state, operator, absolutePath, newContent)
    }

  }
  def echoToFile(state: State, operator: String, absolutePath: String, newContent: String): State = {

    if (!operator.equals(WRITE_TRUNCATE) && !operator.equals(WRITE_CONCAT))
      state.setMessage(args.mkString)
    else {
      val pathTokens = absolutePath.substring(1).split(Directory.SEPARATOR).toList
      val newFile: File = if (operator.equals(WRITE_TRUNCATE)) {
        createOrOverwriteExisting(state, pathTokens, newContent)
      }
      else {
        createOrAppendToExisting(state, pathTokens, newContent)
      }
      val newRoot = updateStructure(state.root, pathTokens, newFile)
      val wdPath = state.wd.path.split("/").toList
      val newWd = newRoot.findDescendant(wdPath).asDirectory
      new State(newRoot, newWd, "")
    }
  }

  def updateStructure(currentDirectory: Directory, pathTokens: List[String], newFile: File): Directory = {
    if (pathTokens.isEmpty)
      currentDirectory
    else if (pathTokens.size == 1)
      currentDirectory.replaceEntry(pathTokens.last, newFile)
    else {
      val oldNextEntry = currentDirectory.findEntry(pathTokens.head)
      currentDirectory.replaceEntry(pathTokens.head, updateStructure(oldNextEntry.asDirectory, pathTokens.tail, newFile))
    }
  }

  def createOrOverwriteExisting(state: State, path: List[String], newContents: String): File = {
    val parentOfExistingFile = state.root.findDescendant(path.init).asDirectory
    val existingFileName = path.last
    if (parentOfExistingFile != null) {
      val existingFile = parentOfExistingFile.findEntry(existingFileName)
      if (existingFile != null) existingFile.asFile.overwrite(newContents)
      else new File(path.init.mkString("/"), existingFileName, newContents)
    }
    else {
      newFile(path, newContents)
    }
  }

  def createOrAppendToExisting(state: State, path: List[String], newContents: String): File = {
    val parentOfExistingFile = state.root.findDescendant(path.init).asDirectory
    val existingFileName = path.last
    if (parentOfExistingFile != null) {
      val existingFile = parentOfExistingFile.findEntry(existingFileName)
      if (existingFile != null) existingFile.asFile.append(newContents)
      else new File(path.init.mkString("/"), existingFileName, newContents)
    }
    else {
      newFile(path, newContents)
    }
  }

  def newFile(path: List[String], contents: String): File = {
    val parentPath = path.init.mkString("/")
    val name = path.last
    new File(parentPath, name, contents)
  }
}
