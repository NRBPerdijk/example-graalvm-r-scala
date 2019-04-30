generateWeatherForecasts <- function(pathToMagicFile) {

  # We're bringing the function contained in the file at the given location into scope
  source(paste(pathToMagicFile))

  # This returns a dataframe, a way for R to store large quantities of data in an ordered manner (kind of like a Database Table...)
  weatherForecast <- magicHappensHere()

  # Like in Scala, the result of the last statement in an R function is its return.
  weatherForecast
}