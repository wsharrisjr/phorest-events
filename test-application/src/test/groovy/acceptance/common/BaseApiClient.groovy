package acceptance.common

import com.jayway.restassured.RestAssured
import com.jayway.restassured.config.ObjectMapperConfig
import com.jayway.restassured.http.ContentType
import com.jayway.restassured.specification.RequestSpecification

import static com.jayway.restassured.config.DecoderConfig.decoderConfig
import static com.jayway.restassured.config.EncoderConfig.encoderConfig
import static com.jayway.restassured.config.RestAssuredConfig.newConfig

class BaseApiClient {

    public static final String DEFAULT_CONTEXT = ''
    public static final String DEFAULT_HOST = 'http://localhost'
    public static final int DEFAULT_HTTP_PORT = 19980

    static RequestSpecification givenApiClient() {
        def config = newConfig()
            .objectMapperConfig(new ObjectMapperConfig(new RestAssuredObjectMapperWrapper(mapper: ObjectMapperFactory.objectMapper)))
            .encoderConfig(encoderConfig().defaultContentCharset('utf-8'))
            .decoderConfig(decoderConfig().defaultContentCharset('utf-8'))

        RestAssured.given().contentType(ContentType.JSON).port(port as int)
            .config(config)
            .baseUri(baseUrl + context)
    }

    private static String getPort() {
        System.getProperty('api.server.port', DEFAULT_HTTP_PORT as String)
    }

    private static getBaseUrl() {
        System.getProperty('api.server.baseUri', DEFAULT_HOST)
    }

    private static getContext() {
        System.getProperty('api.server.context', DEFAULT_CONTEXT)
    }
}
