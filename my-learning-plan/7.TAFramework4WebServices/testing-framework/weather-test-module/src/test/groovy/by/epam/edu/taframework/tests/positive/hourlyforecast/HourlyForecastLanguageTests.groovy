package by.epam.edu.taframework.tests.positive.hourlyforecast

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Aliaksandr_Sheliutsi on 2/9/2017.
 */
class HourlyForecastLanguageTests extends ExampleTestCase{
    def "User should be able to retrieve hourly forecast with russian description"() {

        when: "I retrieve hourly forecast in russian language"
        def response = new WeatherClientBuilder(weatherHourlyForecastApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setLanguage("ru")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ ]*")
        Matcher matcher = pattern.matcher(result.list[0].weather.description)

        then: "Descrition is displayed in russian"
        matcher.matches()
    }
}
