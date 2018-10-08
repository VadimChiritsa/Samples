package by.epam.edu.taframework.tests.negative.dailyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class DailyForecastNegativeTests extends ExampleTestCase {
    def "User should get error message when city is not found"() {

        when: "I retrieve weather for Abrakadabra"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Abrakadabra")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 502 and error message is displayed in response"
        response.statusCode.value() == 502
        result.message.contains("Error")
    }

    def "User should get error message when use unsupported mode"() {

        when: "I retrieve weather with mode email"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .setMode("email")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 400 and error message is displayed in response"
        response.statusCode.value() == 400
        result.message.contains("Error")
    }

    def "User should get temperature in kelvin when units is unsupported"() {
        when: "I retrieve daily forecast in goose units"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .setUnits("goose")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Response status code is 200 and forecast is kelvin range"
        response.statusCode.value() == 200
        result.list[0].temp.day > 208
        result.list[0].temp.day < 333
    }

    def "User get 7 day weather forecast if count more than 16"() {

        when: "I retrieve daily forecast for 17 day"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Mexico")
                .setCount(17)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Forecast in response is for 7 day and status code is 200"
        response.statusCode.value() == 200
        result.list.size() == 7
        result.cnt == 7
    }

    def "User get daily forecast  for 7 days if count is wrong"() {

        when: "I retrieve daily forecast for 999999 day"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Mexico")
                .setCount(9999999)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Forecast in response is for 7 day and status code is 200"
        response.statusCode.value() == 200
        result.list.size() == 7
        result.cnt == 7
    }
}
