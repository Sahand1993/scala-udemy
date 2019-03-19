package lectures.part2oop

import java.lang.ArithmeticException

import exercises.{Cons, Empty, HisList}

import scala.annotation.tailrec

object Exception extends App{

  val x: String = null
  //println(x.length)
  // will crash with NullPointerException

  // 1. Throwing excpetion
  //val aWeirdValue: String = throw new NullPointerException

  // throwable classes extend the Throwable class.
  // Exception and Error are the major Throwable subtypes.

  // 2. How to catch exceptions:
  def getInt(withExceptions: Boolean): Int =
    if(withExceptions) throw new RuntimeException("No int for you!")
    else 42

  val potentialFail = try {
    // code that might fail
    getInt(false)
  }
  catch {

    case e: RuntimeException => 43

  }
  finally {
    // will get executed NO MATTER WHAT
    // finally block is optional
    // does not influence the return type of this expression
    // use finally only for side effects
    println("finally")
  }

  println(potentialFail)

  // 3. how to define your own exceptions
  class MyException extends Exception
  val exception = new MyException
  //throw exception

  /*
    1. Crash your program with an OutOfMemoryError
    2. Crash with a SOError
    3. Pocket calculator
      - add(x, y)
      - subtract(x, y)
      - multiply(x, y)
      - divide(x, y)

      Throw:
        - OverFlowException if add(x, y) exceeds Int.MAX_VALUE
        - UnderFlowException if subtract(x, y) exceeds Int.MIN_VALUE
        - MathCalculationException for division by 0
   */

  // 1.
  def OOMCrasher(acc: HisList[String]): HisList[String] = {
    OOMCrasher(acc.add("Hi again!"))
  }
  //OOMCrasher(Cons("Hi again!", Empty))

  def SOCrasher(dummy: Int): Int = {
    SOCrasher(dummy) + SOCrasher(dummy)
    dummy
  }

  class OverflowException extends Exception

  class UnderFlowException extends Exception

  class MathCalculationException extends Exception

  object PocketCalculator {


    def add(x: Int, y: Int): Int = {
      if (y >= 0) addPos(x, y)
      else subtract(x, -y)
    }

    def addPos(x: Int, y: Int): Int = {
      val out: Int = x + y
      if (out < x) throw new OverflowException
      out
    }

    def subtract(x: Int, y: Int): Int = {
      val out: Int = x - y
      if (out > x) throw new UnderFlowException
      out
    }

    def multiply(x: Int, y: Int): Int = {
      val res: Int = x * y
      if(x < 0 && y < 0 && res < 0) throw new OverflowException
      if(x > 0 && y > 0 && res < 0) throw new OverflowException
      if(x > 0 && y < 0 && res > 0) throw new UnderFlowException
      if(x < 0 && y > 0 && res > 0) throw new UnderFlowException
      res
    }

    def divide(x: Int, y: Int): Int = {
      try {
        x / y
      } catch {
        case e: ArithmeticException => throw new MathCalculationException
      }
    }
  }

class Calculator {
  def addPos(x: Int, y: Int): Int = {
    if (y == 0) x
    else {
      val temp: Int = x + 1
      if (temp < x) throw new OverflowException
      else addPos(temp, y - 1)
    }
  }
}

  println(PocketCalculator.subtract(100, 98))
  println(PocketCalculator.divide(10, 0))
}
