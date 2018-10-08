package by.epam.edu.taframework.tests.negative.hourlyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class HourlyForecastNegativeTests extends ExampleTestCase {
    def "User should get error message when city is not found"() {

        when: "I retrieve weather for Abrakadabra"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Abrakadabra")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 502 and error message is displayed in response"
        response.statusCode.value() == 502
        result.message.contains("Error")
    }

    def "User should get error message when use unsupported mode"() {

        when: "I retrieve weather for in unsupported mode"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Liverpool")
                .setMode("thoughts")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 400 and error message is displayed in response"
        response.statusCode.value() == 400
        result.message.contains("Error")
    }

    def "User should't be able to retrieve hourly forecast for count more than 41"() {

        when: "I retrieve hourly forecast with count = 41"

        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Liverpool")
                .setCount(41)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 200 and count is more than 34"
        response.statusCode.value() == 200
        result.list.size() > 34
        result.cnt > 34
    }

    def "User get daily forecast with more than 34 snapshot even if count is wrong"() {

        when: "I retrieve daily forecast for 9999 day"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Liverpool")
                .setCount(9999)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Forecast in response with more than 34 snapshots and status code is 200"
        response.statusCode.value() == 200
        result.list.size() > 34
        result.cnt > 34
    }

    def "Hourly forecast shows in kelvin only even if units is unsupported"() {

        when: "I retrieve hourly forecast in normalniy units"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setUnits("normalniy")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in kelvin range"
        response.statusCode.value() == 200
        result.list[0].main.temp > 208
        result.list[0].main.temp < 333
    }

}
