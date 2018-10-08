package by.epam.edu.taframework.tests.positive.hourlyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class HourlyForecastCountTests extends ExampleTestCase{
    def "User should be able to retrieve hourly forecast with count=1"() {

        when: "I retrieve hourly forecast with count = 1"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Liverpool")
                .setCount(1)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 200 and count is 1"
        response.statusCode.value() == 200
        result.list.size() == 1
        result.cnt == 1
    }

    def "User should be able to retrieve hourly forecast with count at least 35"() {

        when: "I retrieve hourly forecast with count 35"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Liverpool")
                .setCount(40)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 200 and count is 35"
        response.statusCode.value() == 200
        result.list.size() == 35
        result.cnt == 35
    }


}
