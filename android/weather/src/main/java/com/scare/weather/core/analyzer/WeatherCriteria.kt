package com.scare.weather.core.analyzer

import com.scare.weather.model.WeatherInfo

object WeatherCriteria {
    // 좋은 날씨 기준
    private const val GOOD_TEMP_MIN = 15.0
    private const val GOOD_TEMP_MAX = 22.0
    private const val GOOD_HUMIDITY_MIN = 40
    private const val GOOD_HUMIDITY_MAX = 60
    private const val GOOD_WIND_SPEED_MIN = 1.0
    private const val GOOD_WIND_SPEED_MAX = 5.0

    // 보통 날씨 기준
    private const val MODERATE_TEMP_MIN = 0.0
    private const val MODERATE_TEMP_MAX = 30.0
    private const val MODERATE_HUMIDITY_MIN = 60
    private const val MODERATE_HUMIDITY_MAX = 70
    private const val MODERATE_WIND_SPEED_MIN_LOW = 0.5
    private const val MODERATE_WIND_SPEED_MAX_LOW = 1.0
    private const val MODERATE_WIND_SPEED_MIN_HIGH = 5.0
    private const val MODERATE_WIND_SPEED_MAX_HIGH = 8.0

    // BAD
    private const val BAD_TEMP_MAX = 35
    private const val BAD_TEMP_MIN = -5
    private const val BAD_WIND_SPEED = 17
    private const val BAD_HUMIDITY = 90

    // 날씨 판단 기준 메서드
    fun isTemperatureGood(temp: Double) = temp in GOOD_TEMP_MIN..GOOD_TEMP_MAX

    fun isTemperatureModerate(temp: Double) = temp in MODERATE_TEMP_MIN..MODERATE_TEMP_MAX

    fun isHumidityGood(humidity: Int) = humidity in GOOD_HUMIDITY_MIN..GOOD_HUMIDITY_MAX

    fun isHumidityModerate(humidity: Int) = humidity in MODERATE_HUMIDITY_MIN..MODERATE_HUMIDITY_MAX

    fun isWindSpeedGood(speed: Double) = speed in GOOD_WIND_SPEED_MIN..GOOD_WIND_SPEED_MAX

    fun isWindSpeedBad(speed: Double) = speed > MODERATE_WIND_SPEED_MAX_HIGH

    fun isWindSpeedModerate(speed: Double) =
        (speed in MODERATE_WIND_SPEED_MIN_LOW..MODERATE_WIND_SPEED_MAX_LOW) ||
                (speed in MODERATE_WIND_SPEED_MIN_HIGH..MODERATE_WIND_SPEED_MAX_HIGH)

    fun isBadWeather(weatherInfo: WeatherInfo): Boolean {
        // 비/눈이 오는지 확인
        val isRainingOrSnowing = weatherInfo.precipitationType > 0

        // 온도가 BAD 기준에 해당하는지 확인
        val isBadTemperature =
            weatherInfo.temperature < BAD_TEMP_MIN || weatherInfo.temperature > BAD_TEMP_MAX

        // 습도가 BAD 기준에 해당하는지 확인
        val isBadHumidity = weatherInfo.humidity >= BAD_HUMIDITY

        // 풍속이 BAD 기준에 해당하는지 확인
        val isBadWindSpeed = weatherInfo.windSpeed >= BAD_WIND_SPEED

        // 비나 눈이 오는 경우
        val isBadPrecipitation = isRainingOrSnowing

        // BAD 상태는 하나라도 해당되면 true
        return isBadTemperature || isBadHumidity || isBadWindSpeed || isBadPrecipitation
    }
}