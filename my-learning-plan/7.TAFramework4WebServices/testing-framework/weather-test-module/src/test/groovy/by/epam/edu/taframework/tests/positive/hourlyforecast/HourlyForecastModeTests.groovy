package by.epam.edu.taframework.tests.positive.hourlyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class HourlyForecastModeTests extends ExampleTestCase {

    def "User should be able to retrieve hourly forecast in json format"() {

        when: "I retrieve hourly forecast in json mode"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setMode("json")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 200 and max and min temperature is displayed in json format"
        response.statusCode.value() == 200
        result.list[0].main.temp > 208
        result.list[0].main.temp < 333
    }

    def "User should be able to retrieve hourly forecast in xml format"() {

        when: "I retrieve hourly forecast in xml mode"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setMode("xml")
                .buildClient()
                .send()
        def result = new XmlSlurper().parseText(response.body)

        then: "Hourly forecast should be displayed for 35 days in xml format"
        result.forecast.time.size() == 35
    }
}
