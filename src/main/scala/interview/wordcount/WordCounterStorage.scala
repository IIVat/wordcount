package interview.wordcount

import scala.collection.mutable

object WordCounterStorage {
  //mutable Map only for the sake of performance here
  private val words = mutable.TreeMap[String, Int]()
//  private val words = mutable.HashMap[String, Int]()

  def addOrUpdate(k: String): Option[Int] = words.updateWith(k) {
    x => Option(x.fold(1)(_ + 1))
  }

  def printResult(): Unit = words.foreach { case (k, v) =>
    println(s"$k - $v")
  }
}
