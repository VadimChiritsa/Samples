package by.epam.edu.taframework.tests.positive.current

import com.ihg.middleware.builder.WeatherClientBuilder
import com.ihg.middleware.test.ExampleTestCase
import groovy.json.JsonSlurper
import groovy.json.internal.JsonFastParser
import org.htmlcleaner.CleanerProperties
import org.htmlcleaner.DomSerializer
import org.htmlcleaner.HtmlCleaner
import org.w3c.dom.Document

import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * Created by Aliaksandr_Sheliutsi on 2/8/2017.
 */
/***
 * Possible values: json, xml, html
 */
public class CurrentWeatherModeTests extends ExampleTestCase {
    def "User should be able to retrieve weather in json format"() {

        when: "I retrieve weather in json format"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setMode("json")
                .buildClient()
                .send()
        def result = new JsonFastParser().parse(response.body)

        then: "Max and min temperature is displayed in response"
        response.statusCode.value() == 200
        result.get("main").toValue().temp_max.toFloat() > 200
        result.get("main").toValue().temp_min.toFloat() > 200
    }

    def "User should be able to retrieve weather in xml format"() {

        when: "I retrieve weather in xml format"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setMode("xml")
                .buildClient()
                .send()
        def result = new XmlSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in response"
        result.temperature.@min.toFloat() > 200
        result.temperature.@max.toFloat() > 200
    }

    def "Weather is retrieved in json format even for empty format"() {

        when: "I retrieve weather in empty format"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setMode("")
                .buildClient()
                .send()
        def result = new JsonSlurper().parseText(response.body)

        then: "Max and min temperature is displayed in response"
        response.statusCode.value() == 200
        result.main.temp_max > 200
        result.main.temp_min > 200
    }


    def "User should be able to retrieve weather in html format"() {
        String xPathExpression = "//div[@title][not(img)]/text()"
        when: "I retrieve weather in html format"
        def response = new WeatherClientBuilder(weatherApiHttpClient, appId)
                .byLocation("Brest,fr")
                .setMode("html")
                .buildClient()
                .send()
        Document doc = new DomSerializer(new CleanerProperties()).createDOM(new HtmlCleaner().clean(response.body));
        XPath xpath = XPathFactory.newInstance().newXPath()
        String currentTemp = (String) xpath.evaluate(xPathExpression, doc, XPathConstants.STRING);

        then: "Max and min temperature is displayed in response"
        currentTemp
    }

    def "test Setup block"() {
        setup: "try to get null exception"
        def map = new HashMap()
        map.put(null, "elem")
    }
}
