package by.epam.edu.taframework.tests.positive.dailyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class DailyForecastLocationTests extends ExampleTestCase{

    def "User should be able to retrieve daily forecast for Rome"() {

        when: "I retrieve daily forecast for Rome"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Response status code is 200 and forecast is displayed"
        response.statusCode.value() == 200
        result.list.size() > 0
    }

    def "User should be able to retrieve daily forecast for Rome with countrycode"() {

        when: "I retrieve daily forecast for Rome with countrycode"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome, it")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Response status code is 200 and forecast is displayed"
        response.statusCode.value() == 200
        result.list.size() > 0
    }
}
