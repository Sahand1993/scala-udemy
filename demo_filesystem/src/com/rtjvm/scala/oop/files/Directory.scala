package com.rtjvm.scala.oop.files

import scala.annotation.tailrec

import com.rtjvm.scala.oop.Constants._
import com.rtjvm.scala.oop.filesystem.FileSystemException

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {
  def isRoot: Boolean = name.equals("") && parentPath.equals("")


  def addEntry(newEntry: DirEntry): Directory = new Directory(parentPath, name, contents :+ newEntry)

  def findDescendant(path: List[String]): DirEntry = {
    if (path.isEmpty) this
    else {
      val entry = findEntry(path.head)
      if (entry != null) entry.asDirectory.findDescendant(path.tail)
      else null
    }
  }

  def findEntry(name: String): DirEntry = {
    @tailrec
    def helper(contentsLeft: List[DirEntry]): DirEntry = {
      if (contentsLeft.isEmpty) null
      else if (contents.head.name.equals(name)) contents.head
      else helper(contentsLeft.tail)
    }
    helper(contents)
  }

  def hasEntry(name: String): Boolean = {
    findEntry(name) != null
  }

  def createDir(dirName: String): Directory = {
    new Directory(parentPath, name, contents :+ Directory.empty(name, dirName))
  }

  def getAllFoldersInPath: List[String] = {
    path.split(Directory.SEPARATOR).toList.filter(e => !e.isEmpty)
  }

  def replaceEntry(oldName: String, newEntry: DirEntry): Directory = {
    def replaceContent(newContents: List[DirEntry]): List[DirEntry] = {
      if (newContents.size == contents.size) newContents
      else {
        if (contents(newContents.size).name.equals(oldName)) replaceContent(newContents :+ newEntry)
        else {
          replaceContent(newContents :+ contents(newContents.size))
        }
      }
    }
    if (!contents.map(e => e.name).contains(oldName))
      new Directory(parentPath, name, contents :+ newEntry)
    else
      new Directory(parentPath, name, replaceContent(List()))
  }

  def hisReplaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)

  def removeEntry(entryName: String): Directory = {
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)))
  }
  override def asDirectory: Directory = this

  override def asFile: File = throw new FileSystemException("directory cannot be converted to file")

  override def getType: Int = DIR

}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String): Directory =
    new Directory(parentPath, name, List())
}

object Run extends App {
  val myDir = new Directory("", "myDir", List(new Directory("/mydir/", "a", List()), new Directory("/myDir/", "b", List())))
  println(myDir.replaceEntry("a", new Directory("/myDir/", "new", List())).findEntry("notinlist"))
}