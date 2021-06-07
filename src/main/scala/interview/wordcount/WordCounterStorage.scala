package interview.wordcount

import scala.collection.IterableOnce._
import scala.collection.immutable.TreeSet
import scala.collection.mutable

object WordCounterStorage {
  implicit val ord: Ordering[(String, Int)] = (x: (String, Int), y: (String, Int)) => {
    val (s1, i1) = x
    val (s2, i2) = y

    if (i1 > i2) -1
    else if (i1 < i2) 1
    else s1.toLowerCase().compare(s2.toLowerCase())
  }

  //mutable Map only for the sake of performance here
  private val words = mutable.HashMap[String, Int]()


  def addOrUpdate(k: String): Option[Int] = {
    words.updateWith(k) {
      x => Option(x.fold(1)(_ + 1))
    }
  }

  def printResult(): Unit = words.to(TreeSet).foreach {
    case (k, v) =>
      println(s"$k - $v")
  }
}
