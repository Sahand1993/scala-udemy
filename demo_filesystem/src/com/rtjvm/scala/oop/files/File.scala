package com.rtjvm.scala.oop.files

import com.rtjvm.scala.oop.Constants._
import com.rtjvm.scala.oop.filesystem.FileSystemException

class File(override val parentPath: String, override val name: String, contents: String) extends DirEntry(parentPath, name) {

  override def asDirectory: Directory =
    throw new FileSystemException("file cannot be converted to directory.")

  override def asFile: File = this

  override def getType: Int = FILE

}

object File {

  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")

}

