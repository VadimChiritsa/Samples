package com.ihg.middleware.test

import com.ihg.middleware.client.HttpClient
import com.ihg.middleware.context.ExampleContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = ExampleContext.class)
abstract class ExampleTestCase extends Specification {
    /**
     * Client to call Weather API.
     */
    @Autowired
    HttpClient weatherApiHttpClient

    @Autowired
    HttpClient weatherDailyForecastApiHttpClient

    @Autowired
    HttpClient weatherHourlyForecastApiHttpClient

    @Autowired
    HttpClient searchRegionsApiHttpClient

    @Autowired
    String appId

}
