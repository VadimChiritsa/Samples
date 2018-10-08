package by.epam.edu.taframework.tests.positive.dailyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper
import groovy.json.internal.JsonFastParser

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class DailyForecastCountTests extends ExampleTestCase{

    def "User should be able to retrieve daily forecast for 1 day"() {

        when: "I retrieve daily forecast for 1 day"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Mexico")
                .setCount(1)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Forecast in response is for 1 day and status code is 200"
        response.statusCode.value() == 200
        result.list.size() == 1
        result.cnt == 1
    }

    def "User should be able to retrieve daily forecast for 16 day"() {

        when: "I retrieve daily forecast for 16 day"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Mexico")
                .setCount(16)
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Forecast in response is for 16 day and status code is 200"
        response.statusCode.value() == 200
        result.list.size() == 16
        result.cnt == 16
    }
    //Cannot decide is it a bug or a feature: if number of days is wrong the results shows for 7 days



}
