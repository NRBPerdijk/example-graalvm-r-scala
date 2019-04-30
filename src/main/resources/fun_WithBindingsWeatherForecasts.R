generateWeatherForecasts <- function(pathToMagicFile) {
  # We're bringing the function contained in the file at the given location into scope
  source(paste(pathToMagicFile))

  # This returns a dataframe, a way for R to store large quantities of data in an ordered manner (kind of like a Database Table...)
  weatherForecast <- magicHappensHere()

  # We use the Scala Domain object provided through GraalVM bindings to get ourselves an instance of the Scala wrapper containing a List of WeatherForecast
  weatherForecastList <- Domain$weatherForecastList()

  # We're looping over all the entries in the dataframe and getting the corresponding elements from the proper columns/rows
  for (count in seq(weatherForecast$humidity)) {
    # Here we use the provided add method to add a new WeatherForecast to the List.
    # Just like R, this Scala class returns a new, updated instance (rather than updating the old),
    # so we're reassigning the variable to this new instance.
    weatherForecastList <- weatherForecastList$add(
      # We are using Domain to construct properly Typed Scala instances of Domain classes.
      # Anything illegal (like putting a String in an Int, or Percentage) will cause an exception at the location of insertion! (Instead of after parsing!)
      # Yay for proper stacktraces!
      Domain$weatherForecast(
        Domain$percentage(weatherForecast$humidity[count]),
        Domain$windForecast(Domain$windSpeed(weatherForecast$windScale[count], weatherForecast$windSpeed[count]), weatherForecast$windDirection[count]),
        Domain$percentage(weatherForecast$sunshine[count]),
        Domain$temperature(weatherForecast$temperature[count], weatherForecast$temperatureScale[count]),
        Domain$chanceOfRain(Domain$percentage(weatherForecast$chanceOfRain[count]))
      )
    )
  }

  #like in Scala, the result of the last statement in an R function is its return.
  weatherForecastList
}