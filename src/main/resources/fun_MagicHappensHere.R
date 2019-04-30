# Some very impressive R stuff happens here. (Well, we're pretending it does, anyway)
# This function returns a data.frame containing a number of weather forecasts
magicHappensHere <- function() {
    max_amount_forecasts <- 10

    # Creating a dataframe, which will be the resulttype of this "magic" R function
    weatherForecasts <- data.frame(
      humidity = rep(NA_integer_, max_amount_forecasts),
      windSpeed = rep(NA_integer_, max_amount_forecasts),
      windScale = rep(NA_integer_, max_amount_forecasts),
      windDirection = rep(NA_integer_, max_amount_forecasts),
      sunshine = rep(NA_integer_, max_amount_forecasts),
      temperature = rep(NA_integer_, max_amount_forecasts),
      temperatureScale = rep(NA_integer_, max_amount_forecasts),
      chanceOfRain = rep(NA_integer_, max_amount_forecasts),
      stringsAsFactors = FALSE
    )

    forecastMax <- 10

    # Filling the dataframe with forecastMax entries
    for (count in 1:forecastMax) {
        weatherForecasts$humidity[count] <- 5 + count
        weatherForecasts$windSpeed[count] <- 5 + count
        weatherForecasts$windScale[count] <- "Beaufort"
        weatherForecasts$windDirection[count] <- "East"
        weatherForecasts$sunshine[count] <- 23 + count
        weatherForecasts$temperature[count] <- 15 + count
        weatherForecasts$temperatureScale[count] <- "Celsius"
        weatherForecasts$chanceOfRain[count] <- 42 + count
    }
    # Like in Scala, the result of the last statement in an R function is its return.
    weatherForecasts

}
