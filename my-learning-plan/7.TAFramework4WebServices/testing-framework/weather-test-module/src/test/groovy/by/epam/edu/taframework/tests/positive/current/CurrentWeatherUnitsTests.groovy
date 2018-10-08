package by.epam.edu.taframework.tests.positive.current

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase

/**
 * Created by Aliaksandr_Sheliutsi on 2/8/2017.
 */

/***
 * Possible values: metric, imperial, standard is default(even with wrong)
 */
class CurrentWeatherUnitsTests extends ExampleTestCase {
    def "User should be able to retrieve temperature in kelvin"() {

        when: "I retrieve weather in default units"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setMode("xml")
                .setUnits("standard")
                .buildClient()
                .send()
                .body
        def result = new XmlSlurper().parseText(response)

        then: "Max and min temperature is displayed in kelvin"
        result.temperature.@unit == "kelvin"
        result.temperature.@max.toFloat() > 208
        result.temperature.@max.toFloat() < 333
    }

    def "User should be able to retrieve temperature in celsius"() {

        when: "I retrieve weather in metrics units"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Svetlogorsk,ru")
                .setMode("xml")
                .setUnits("metric")
                .buildClient()
                .send()
                .body
        def result = new XmlSlurper().parseText(response)

        then: "Max and min temperature is displayed in celsius"
        result.temperature.@unit == "celsius"
        result.temperature.@max.toFloat() > -65
        result.temperature.@max.toFloat() < 60
    }

    def "User should be able to retrieve temperature in fahrenheit"() {

        when: "I retrieve weather in xml format"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Svetlogorsk,ru")
                .setMode("xml")
                .setUnits("imperial")
                .buildClient()
                .send()
        def result = new XmlSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in fahrenheit"
        result.temperature.@unit == "fahrenheit"
        result.temperature.@max.toFloat() > -85
        result.temperature.@max.toFloat() < 140
    }
}


