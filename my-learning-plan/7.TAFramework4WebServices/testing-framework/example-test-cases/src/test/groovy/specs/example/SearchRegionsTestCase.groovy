package specs.example

import com.ihg.middleware.test.ExampleTestCase
import org.custommonkey.xmlunit.XMLUnit

class SearchRegionsTestCase extends ExampleTestCase{
    def "User should be able to find cities by keyword"() {
        def locationValue = "Brest"
        def anotherLocation = "London"
        def modeValue = "xml"
        def typeValue = "like"


        when: "I retrieve cities by keyword"
            def response = searchRegionsApiHttpClient.send(
                    REQUEST_PARAMS_STRING : "q={location}&type={type}&mode={mode}",
                    REQUEST_PARAMS_VARIABLES :
                            [
                                    location : locationValue,
                                    type  : typeValue,
                                    mode  : modeValue
                            ]
            )

        def anotherResponse = searchRegionsApiHttpClient.send(
                REQUEST_PARAMS_STRING : "q={location}&type={type}&mode={mode}",
                REQUEST_PARAMS_VARIABLES :
                        [
                                location : anotherLocation,
                                type  : typeValue,
                                mode  : modeValue
                        ]
        )
            def result = new XmlSlurper().parseText(response)


        then: "All countries with this city are found"
            !XMLUnit.compareXML(response, anotherResponse).identical()
    }
}