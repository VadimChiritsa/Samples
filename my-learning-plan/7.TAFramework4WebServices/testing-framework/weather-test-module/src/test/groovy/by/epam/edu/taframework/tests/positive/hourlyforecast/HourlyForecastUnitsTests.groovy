package by.epam.edu.taframework.tests.positive.hourlyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class HourlyForecastUnitsTests extends ExampleTestCase {
    def "User should be able to retrieve hourly forecast in kelvin"() {

        when: "I retrieve hourly forecast in default units"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setUnits("")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in kelvin range"
        response.statusCode.value() == 200
        result.list[0].main.temp > 208
        result.list[0].main.temp < 333
    }

    def "User should be able to retrieve hourly forecast in celsius"() {

        when: "I retrieve hourly forecast in metric units"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setUnits("metric")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in celsius range"
        response.statusCode.value() == 200
        result.list[0].main.temp > -65
        result.list[0].main.temp < 60
    }

    def "User should be able to retrieve hourly forecast in fahrenheit"() {

        when: "I retrieve hourly forecast in imperial units"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setUnits("imperial")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in fahrenheit range"
        response.statusCode.value() == 200
        result.list[0].main.temp > -85
        result.list[0].main.temp < 140
    }
}
