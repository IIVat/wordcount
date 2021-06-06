package interview.wordcount

import akka.NotUsed
import akka.actor.{ActorSystem, Cancellable}
import akka.stream._
import akka.stream.scaladsl._
import interview.wordcount.javatask.{CharacterReader, SlowCharacterReaderImpl}

import java.io.EOFException
import java.util.concurrent.TimeUnit
import scala.collection.mutable
import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.util.control.NonFatal


object SlowCharacterCounterApp extends App {
  type ReaderId = Int
  type Word = String

  implicit val sys: ActorSystem = ActorSystem("wordcount-sys")
  implicit val ec: ExecutionContextExecutor = sys.dispatcher

  val startTime = System.nanoTime()

  val readersNumber = 10
  val readers = generateSlowCharReaders(readersNumber)

  charSource(readers)
    .statefulMapConcat {
      () =>
        //Accumulates chars to word for every reader
        //when char is not a Letter, the accumulated word should be saved to storage
        //then the word should be reset for the reader
        val readerWordMap = mutable.HashMap.empty[ReaderId, Word]
        idAndCh =>
          val (id, ch) = idAndCh
          if (ch.isLetter) {
            val chLowerCase = ch.toLower
            readerWordMap.updateWith(id)(x => Option(x.fold(chLowerCase.toString)(_ + chLowerCase)))
              .foreach(_ => ())
          } else {
            readerWordMap
              .filter { case (_, value) => value.nonEmpty && value.length > 1 }
              .get(id)
              //in a real app I wouldn't use the call here
              .map(WordCounterStorage.addOrUpdate)
              .foreach(_ => readerWordMap.update(id, ""))
          }
        readerWordMap
    }.async
    .zipLatest(printSubtotalEvery10Sec)
    .run()
    .onComplete {
      _ =>
        WordCounterStorage.printResult()
        println(s"================Final result after ${getElapsedTime(startTime)}:================")
        sys.terminate()
    }

  def generateSlowCharReaders(n: Int): Seq[(ReaderId, SlowCharacterReaderImpl)] =
    (0 until n).map(id => id -> new SlowCharacterReaderImpl())

  def charSource(readers: Seq[(ReaderId, CharacterReader)]): Source[(ReaderId, Char), NotUsed] =
    Source.fromGraph(GraphDSL.create() { implicit builder ⇒
      import GraphDSL.Implicits._

      val mergeChars: UniformFanInShape[(ReaderId, Char), (ReaderId, Char)] =
        builder.add(Merge[(ReaderId, Char)](readers.size))

      //Generates separate Source for every CharReader and then merges them
      for (charReader <- readers) {
        println(s"Char reader [id = ${charReader._1}] was created")

        Source
          .repeat()
          .map(_ => charReader._2.nextCharacter())
          .recover { case NonFatal(_) => '$' }
          .map(ch => charReader._1 -> ch).async ~> mergeChars
      }

      SourceShape(mergeChars.out)
    })


  def printSubtotalEvery10Sec: Source[Unit, Cancellable] = {
    Source
      .tick(0.second, 10.seconds, ())
      .map { _ =>
        println(s"============Result after ${getElapsedTime(startTime)} sec:============")
        WordCounterStorage.printResult()
      }
  }

  def getElapsedTime(startTime: Long) = {
    val elapsedTime = System.nanoTime() - startTime
    TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)
  }

  sys.registerOnTermination(readers.map(_._2.close()))
}
