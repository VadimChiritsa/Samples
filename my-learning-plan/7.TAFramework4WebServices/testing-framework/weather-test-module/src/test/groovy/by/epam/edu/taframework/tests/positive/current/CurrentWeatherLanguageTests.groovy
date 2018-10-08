package by.epam.edu.taframework.tests.positive.current

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Aliaksandr_Sheliutsi on 2/8/2017.
 */
// Possible values %))
class CurrentWeatherLanguageTests extends ExampleTestCase {
    def "User should be able to get weather description in russian"() {

        when: "I retrieve weather with russian language"

        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Paris,fr")
                .setLanguage("ru")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ ]*")
        Matcher matcher = pattern.matcher(result.weather[0].description)

        then: "Weather description is displayed in Russian"
        matcher.matches()
    }
}
