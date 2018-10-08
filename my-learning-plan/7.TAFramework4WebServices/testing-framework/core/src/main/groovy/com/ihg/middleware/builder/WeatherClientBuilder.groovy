package com.ihg.middleware.builder

import com.ihg.middleware.client.HttpClient
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Aliaksandr_Sheliutsi on 2/10/2017.
 */
class WeatherClientBuilder {
    StringBuilder params

    //@Autowired
    //String appId

    HttpClient client
    //It turns out that we can send all parameters except
    // "appid" in post request body in type "urlencoded"

    WeatherClientBuilder(HttpClient client, String appId) {
        this.client = (HttpClient)client.clone()
        this.params = new StringBuilder().append("?appid=" + appId)
    }

    WeatherClientBuilder byLocation(String location) {
        params.append("&q=" + location)
        this
    }

    WeatherClientBuilder setMode(String format) {
        params.append("&mode=" + format)
        this
    }

    WeatherClientBuilder setLanguage(String lang) {
        params.append("&lang=" + lang)
        this
    }

    WeatherClientBuilder setCount(int count) {
        params.append("&cnt=" + count)
        this
    }

    WeatherClientBuilder setUnits(String units) {
        params.append("&units=" + units)
        this
    }

    HttpClient buildClient() {
        client.hostUrl += params.toString()
        client
    }

}
