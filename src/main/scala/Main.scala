import java.util

import domain.weatherforecast._
import org.graalvm.polyglot.{Context, Source}

import scala.util.{Failure, Success, Try}
import scala.collection.JavaConverters._

object Main extends App {

  // We need to initialise a GraalContext that will do the mediation between the JVM languages and R
  val context: Context = Context.newBuilder("R").allowAllAccess(true).build()

  // Next, we need to create a Source, which needs to know what language it features and where to find the code.
  val sourceNoBindings: Source = Source.newBuilder("R", Main.getClass.getResource("fun_NoBindingsWeatherForecasts.R")).build()

  /*
   * We use the graal context to convert the source into a function.
   * Because R is dynamically typed, the compiler cannot help you here: it trusts that you give it correct instructions!
   * This also means that you may ( => DEFINITELY) want to wrap any call to this function in a Try to prevent explosions!
   *
   * We need to tell our compiler what kind of function this new Source represents.
   * In this case it is a function that takes one argument:
   *   - a path to another R function (which mocks the magic that R is good at)
   * And it returns... something rather complex: a Map of Strings, that refer to Lists that contain... something we can't usefully Type because it will actually be different things!
   */
  val rNoBindingsWeatherForecasts: String => util.Map[String, util.ArrayList[_]] =
    context.eval(sourceNoBindings).as(classOf[String => util.Map[String, util.ArrayList[_]]])
  /*
   * Wow, the above is quite a dragon of a type signature!
   * Also: nested Maps? Turns out R Dataframes are Maps, not Lists, by JVM standards (this makes sense: either you declare a key, or you get an auto-generated numeric one).
   * We also can't assert a Type for the values in the second Map (because we expect both Strings and Ints), thats going to lead to casting?.
   * Finally, Graal is giving us Java Maps when we're using Scala, that's just sad, because Scala Map is much more convenient...
   */

  private val path = Main.getClass.getResource("fun_MagicHappensHere.R").getPath
  Try(rNoBindingsWeatherForecasts(path)) match {
    case Failure(f) => print(f)
    case Success(s) =>
      val resultAsScala: Map[String, List[_]] = s.asScala.toMap.map(entry => entry._1 -> entry._2.asScala.toList)

      val humidities: List[Int] = resultAsScala("humidity").asInstanceOf[List[Int]] // We need to do nasty casting, because the returntype is not uniform
      val temperatures: List[Int] = resultAsScala("temperature").asInstanceOf[List[Int]]
      val temperatureScale: List[String] = resultAsScala("temperatureScale").asInstanceOf[List[String]]
      /*
       * We are omitting a bunch of things here:
       *   - there are more return values that need to be extracted (which won't tell us if we're being exhaustive or not)
       *   - these return values need to be fit inside proper domain objects for further typesafe treatment, so we'll need to stitch elements from each list together...
       *
       * But already, we can see that this is:
       *   - very verbose
       *   - very error prone (it takes a lot of trial and error to get it right)
       *   - very brittle (it is very easy for a change somewhere else to break this parsing in half)
       *   - annoying to do!
       * If something is wrong (say, a column is missing), we get errors when parsing, NOT where the actual mistake is made!
       *
       * So... how could we do this differently?
       */
      println("Unmitigated R return (have fun parsing!):")
      println("=========================================")
      println(s)
      println("=========================================")
  }

  /*
   * Exposing bindings is an interesting way to share functionality between languages.
   * This command makes an instance of the Domain class available under the "Domain" accessor.
   */
  context.getBindings("R").putMember("Domain", new Domain)

  // This source will use the provided Domain instance to create objects as they have been defined in the Scala domain.
  val sourceWithBindings: Source = Source.newBuilder("R", Main.getClass.getResource("fun_WithBindingsWeatherForecasts.R")).build()

  // This function signature is a lot cleaner than the one above. It is also completely Scala, meaning we do not have to do ANY parsing.
  val rMagicWithBindings: String => WeatherForecastList = context.eval(sourceWithBindings).as(classOf[String => WeatherForecastList])

  // Remember to always put a call to R in a Try block, R often resorts to throwing RuntimeExceptions.
  Try(rMagicWithBindings(path)) match {
    case Failure(f) => print(f)
    case Success(weatherForecastList) =>
      println("")
      println("Fancy Domain object return:")
      println("=========================================")
      // We get back a WeatherForecastList, which is a wrapper for List[WeatherForecast].
      // Now we can work with the results WITHOUT any parsing: simply take out the List and do your operations (here we print them one by one).
      weatherForecastList.asScalaList.foreach(forecast => println(forecast))
      println("=========================================")
  }

}
