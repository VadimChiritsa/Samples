package by.epam.edu.taframework.tests.positive.dailyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class DailyForecastModeTests extends ExampleTestCase {
    def "User should be able to retrieve daily forecast in json mode"() {

        when: "I retrieve daily forecast in json format"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .setMode("json")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Response status code is 200 and forecast is displayed properly for json format"
        response.statusCode.value() == 200
        result.list.size() > 0
    }

    def "User should be able to retrieve daily forecast in xml mode"() {

        when: "I retrieve daily forecast in xml format"
        def response = new WeatherClientBuilder(weatherDailyForecastApiHttpClient, appId)
                .byLocation("Rome")
                .setMode("xml")
                .buildClient()
                .send()
        def result = new XmlSlurper().parseText(response.body)

        then: "Forecast is displayed for 7 days in xml format"
        result.forecast.time.size() == 7
    }
}
