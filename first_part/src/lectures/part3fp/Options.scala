package lectures.part3fp

import scala.util.Random

object Options extends App {
  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None
  println(myFirstOption)
  println(noOption)

  // unsafe APIs
  def unsafeMethod(): String = null
  val result = Option(unsafeMethod())
  println(result)

  // using options in chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // if you DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()

  // functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) // UNSAFE - DO NOT USE THIS

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(_ / 2 == 2))
  println(noOption.flatMap((x: Int) => Option(x * 2)))

  // for-comprehensions

  /*
    Excercise
   */
  val config: Map[String, String] = Map(
    // fetched from elsewhere
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // try to establish a connection, if successful - print the connect method
  val host = config.get("host")
  val port = config.get("port")

  val connection = host.flatMap( (hostName: String) => port.flatMap(portNo => Connection(hostName, portNo)) )
  val connectionStatus = connection.map(connection => connection.connect)

  connectionStatus.foreach(println)

  val newConnection = for {
    hostName <- host
    portNo <- port
  } yield {
    Connection(hostName, portNo).map(connection => connection.connect)
  }
  newConnection.foreach(println)
  println("sep")
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port)
        .map(connection => connection.connect)))
    .foreach(println)

  // for-comprehension equivalent
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)
}
