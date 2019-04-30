package domain.weatherforecast

case class WeatherForecast(humidity: Percentage, windForecast: WindForecast, sunshine: Percentage, temperature: Temperature, chanceOfRain: ChanceOfRain)

case class Temperature(degrees: Int, temperatureScale: String)

case class Percentage(percent: Double)

case class WindForecast(windSpeed: WindSpeed, direction: String)

case class WindSpeed(scale: String, speed: Int)

case class ChanceOfRain(chance: Percentage)

case class WeatherForecastList(asScalaList: List[WeatherForecast]) {
  def add(weatherForecast: WeatherForecast): WeatherForecastList = this.copy(weatherForecast :: asScalaList)
}
