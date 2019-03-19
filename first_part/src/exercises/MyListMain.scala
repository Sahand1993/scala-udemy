package exercises

object MyListMain extends App {
  var list: MyList = new MyListImp
  list = list.add(1)
  list = list.add(10)
  println(list.toString())
}
