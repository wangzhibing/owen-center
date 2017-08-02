package com.owen.designpatterns.observers.demo1;

/**
 * Created by owen on 17/8/1.
 */
public class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        weatherData.serMeasurements(12f,13f,14f);
        weatherData.serMeasurements(22f,23f,24f);
        weatherData.serMeasurements(32f,33f,34f);
    }
}

