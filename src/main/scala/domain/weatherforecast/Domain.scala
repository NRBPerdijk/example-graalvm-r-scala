package domain.weatherforecast

 /*
  * Our Domain object functions as a factory for our domain-related classes.
  * It has methods that create new instances of these classes, which can then safely be used from another Context.
  */
class Domain {
  def weatherForecastList(): WeatherForecastList = WeatherForecastList(List())
  def percentage(percent: Int): Percentage = Percentage(percent: Double)
  def temperature(degrees: Int, temperatureScale: String): Temperature = Temperature(degrees: Int, temperatureScale: String)
  def chanceOfRain(chance: Percentage): ChanceOfRain = ChanceOfRain(chance: Percentage)
  def windSpeed(scale: String, speed: Int): WindSpeed = WindSpeed(scale: String, speed: Int)
  def windForecast(windSpeed: WindSpeed, direction: String): WindForecast = WindForecast(windSpeed: WindSpeed, direction: String)
  def weatherForecast(humidity: Percentage, windForecast: WindForecast, sunshine: Percentage, temperature: Temperature, chanceOfRain: ChanceOfRain): WeatherForecast =
    WeatherForecast(humidity, windForecast, sunshine, temperature, chanceOfRain)
}
