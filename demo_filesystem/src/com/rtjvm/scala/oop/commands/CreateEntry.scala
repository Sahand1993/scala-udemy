package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exists")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separators")
    } else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name")
    } else {
      addEntry(state, name)
    }
  }

  def checkIllegal(str: String): Boolean

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
    if (path.isEmpty) currentDirectory.addEntry(newEntry)
    else {
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
    }
  }

  def addEntry(state: State, name: String): State = {
    val wd = state.wd

    // 1. get a hold of all the directories in the full path
    val allDirsInPath: List[String] = wd.getAllFoldersInPath

    // 2. Create new directory entry in the working directory.
    val newDir = doCreateEntry(state)

    // 3. Update the whole directory structure starting from the root.
    // Directory structure is IMMUTABLE
    val newRoot = updateStructure(state.root, allDirsInPath, newDir)

    // 4. Find new working directory instance given wd's full path, in the new directory structure
    val newWd = newRoot.findDescendant(allDirsInPath).asDirectory

    State(newRoot, newWd)
  }

  def doCreateEntry(state: State): DirEntry
}
