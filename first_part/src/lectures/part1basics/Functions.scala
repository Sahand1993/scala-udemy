package lectures.part1basics

object Functions extends App {

  def aFunction(a: String, b: Int) = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterlessFunction(): Int = 42
  println(aParameterlessFunction())
  println(aParameterlessFunction)

  def aRepeatedFunction(aString: String, n: Int): String = { // WHEN YOU NEED LOOPS, USE RECURSION
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n - 1)
  }

  println(aRepeatedFunction("Hi Again! ", 3))

  def aFunctionWithSideEffects(aString: String): Unit = {
    println(aString)
  }

  aFunctionWithSideEffects("My name is Lennart Bladh")

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n-1)
  }

  /*
    1. A greeting function (name, age) => "Hi, my name is $name and I am $age years old"
    2. Factorial function
    3. Fibonacci function
      f(1) = 1
      f(2) = 1
      f(n) = f(n - 1) + f(n - 2)
    4. Tests if a number is prime
  */
  // 1.
  def greeting(name: String, age: Int): String = {
    "Hi, my name is " + name + " and I am " + age + " years old"
  }
  println(greeting("Sahand", 25))

  // 2.
  def factorial(n: Int): Int = {
    if (n == 1) 1
    else n * factorial(n-1)
  }
  println(factorial(5))

  // 3.
  def fibonacci(n: Int): Int = {
    if (n <= 2) 1
    else fibonacci(n - 1) + fibonacci(n - 2)
  }
  println(fibonacci(7))

  // 4.
  def isPrime(n: Int): Boolean = {
    def divisableByAny(primeCandidate: Int, divisor: Int): Boolean = {
      if (divisor == 1) false
      else if (primeCandidate % divisor == 0) true
      else divisableByAny(primeCandidate, divisor-1)
    }
    !divisableByAny(n, n-1)
  }
  val x: Int = 2003
  println(if(isPrime(x)) x + " is prime" else x + " is not prime")
  println(isPrime(37*10))
}
