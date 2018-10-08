package by.epam.edu.taframework.tests.negative.current

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class CurrentWeatherNegativeTests extends ExampleTestCase {

    def "User should get error message when city is not found"() {

        when: "I retrieve weather for Abrakadabra"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Abrakadabra")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 502 and error message is displayed in response"
        response.statusCode.value() == 502
        result.message.contains("Error")
    }

    def "User should get error message when use unsupported mode"() {

        when: "I retrieve weather for in lol mode"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Gomel")
                .setMode("lol")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 400 and error message is displayed in response"
        response.statusCode.value() == 400
        result.message.contains("Error")
    }

    def "User should get report in kalvin when units is unsupported"() {

        when: "I retrieve weather in pascal units"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Vitebsk")
                .setMode("xml")
                .setUnits("pascal")
                .buildClient()
                .send()
        def result = new XmlSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in kelvin"
        result.temperature.@unit == "kelvin"
        result.temperature.@max.toFloat() > 208
        result.temperature.@max.toFloat() < 333
    }
}
