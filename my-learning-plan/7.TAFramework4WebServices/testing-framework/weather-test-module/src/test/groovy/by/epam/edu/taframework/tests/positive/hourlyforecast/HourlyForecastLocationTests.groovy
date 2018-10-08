package by.epam.edu.taframework.tests.positive.hourlyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class HourlyForecastLocationTests extends ExampleTestCase{
    def "User should be able to retrieve hourly forecast for Liverpool"() {

        when: "I retrieve hourly forecast for Liverpool"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Liverpool")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 200 and max and min temperature is displayed"
        response.statusCode.value() == 200
        result.list[0].main.temp > 208
        result.list[0].main.temp < 333
    }

    def "User should be able to retrieve hourly forecast for Liverpool with countrycode"() {

        when: "I retrieve hourly forecast for Liverpool with countrycode"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,by")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Status code is 200 and max and min temperature is displayed"
        response.statusCode.value() == 200
        result.list[0].main.temp > 208
        result.list[0].main.temp < 333
    }
}
