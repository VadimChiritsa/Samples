package by.epam.edu.taframework.tests.positive.current

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.internal.JsonFastParser

/**
 * Created by Aliaksandr_Sheliutsi on 2/8/2017.
 */
class CurrentWeatherLocationTests extends ExampleTestCase {
    def "User should be able to retrieve weather for different locations"() {
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation(location)
                .buildClient()
                .send()
        def result = new JsonFastParser().parse(response.body)

        expect:
        response.statusCode.value() == 200
        result.get("main").toValue().temp_max.toFloat() > 200
        result.get("main").toValue().temp_min.toFloat() > 200

        where:
        location    | _
        "Brest"     | _
        "Brest,by"  | _
        "Monaco"    | _
    }

}
