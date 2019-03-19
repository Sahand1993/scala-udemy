package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App{
  def factorial(n: Int): Int =
    if(n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println("Computed factorial of " + n)

      result
    }

  //println(factorial(10))
  //println(factorial(5000))

  def anotherFactorial(n: Int): BigInt = {
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator)

    factHelper(n, 1)
  }

  /*
    anotherFactorial(10) = factHelper(10, 1)
    = factHelper(9, 10 * 1)
    = factHelper(8, 9 * 10 * 1)
    = factHelper(7, 8 * 9 * 10 * 1)
    = ...
    = factHelper(2, 3 * 4 * ... * 10 * 1)
    = factHelper(1, 2 * 3 * ... * 10 * 1) = 10!
   */
  //println(anotherFactorial(20000))

  // WHEN YOU NEED LOOPS, USE _TAIL_ RECURSION

  /*
    1. Concatenate a string n times using tail recursion
   */
  def concatNTimes(s: String, n: Int): String = {
    def concatHelper(accum: String, timesLeft: Int): String = {
      if (timesLeft == 0) accum
      else concatHelper(accum + s, timesLeft-1)
    }
    concatHelper("", n)
  }
  println(concatNTimes("HiAgain!", 4))

  /*
    2. isPrime that is tail recursive
   */
  def isPrime(n: BigInt): Boolean = {
    if (n <= 1) return false
    def isPrimeHelper(divisor: BigInt): Boolean = {
      if (divisor <= 1) true
      else if (n % divisor == 0) false
      else isPrimeHelper(divisor - 1)
    }
    isPrimeHelper(n / 2)
  }
  println(isPrime(13499348))

  /*
    Fibonacci function that is tail recursive
   */
  def fibonacci(n: Int): Int = {
    def helper(count: Int, last: Int, lastlast: Int): Int = {
      if (count == n) last + lastlast
      else helper(count + 1, last + lastlast, last)
    }
    if (n <= 2) 1
    else helper(3, 1, 1)
  }
// 1, 1, 2, 3, 5
  println(fibonacci(5))
}
