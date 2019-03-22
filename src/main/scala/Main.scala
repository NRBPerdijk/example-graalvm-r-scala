import org.graalvm.polyglot.{Context, Source}

object Main extends App {

  // We need to initialise a GraalContext that will do the mediation between the JVM languages and R
  val context: Context = Context.newBuilder("R").allowAllAccess(true).build()

  // Next, we need to create a Source, which needs to know what language it features and where to find the code.
  val source: Source = Source.newBuilder("R", Main.getClass.getResource("funHelloWorld.R")).build()

  /*
   * Then, we need to tell our compiler what kind of function this new Source represents.
   * In this case it is a function that doesn't take an argument and returns a String.
   * We use the graal context to convert the source into the function.
   * Because R is very dynamically typed, the compiler cannot help you here: it trusts that you give it correct instructions!
   * This also means that you may want to wrap any call to this function in a Try!
    */
  val rHelloWorld: () => String = context.eval(source).as(classOf[() => String])

  /*
   * Finally, we can run the function. Because it doesn't take any arguments, we don't provide any.
   * Let's also print the returned String, for good measure.
   */
  println(s"Also printing the return String: ${rHelloWorld()}")
}
