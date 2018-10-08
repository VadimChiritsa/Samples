package by.epam.edu.taframework.tests.positive.dailyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class DailyForecastUnitsTests extends ExampleTestCase {
    def "User should be able to retrieve daily forecast in kelvin"() {

        when: "I retrieve daily forecast in kelvin"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .setUnits("standard")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Response status code is 200 and forecast is kelvin range"
        response.statusCode.value() == 200
        result.list[0].temp.day > 208
        result.list[0].temp.day < 333
    }

    def "User should be able to retrieve daily forecast in celsius"() {

        when: "I retrieve daily forecast in celsius"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .setUnits("metric")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Response status code is 200 and forecast is celsius range"
        response.statusCode.value() == 200
        result.list[0].temp.day > -65
        result.list[0].temp.day < 60
    }

    def "User should be able to retrieve daily forecast in fahrenheit"() {

        when: "I retrieve daily forecast in fahrenheit"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .setUnits("imperial")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Response status code is 200 and forecast is fahrenheit range"
        response.statusCode.value() == 200
        result.list[0].temp.day > -85
        result.list[0].temp.day < 140
    }
}
