package acceptance.steps.common

import cucumber.api.DataTable
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import org.apache.http.HttpStatus

import static acceptance.common.AssertUtils.assertValues

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

Before {
    ResponseStorage.clearResponse()
}

Then(~'^status code (\\d+) returned$') { int expectedStatusCode ->
    assert ResponseStorage.getResponse().statusCode == expectedStatusCode
}

Then(~'^access forbidden$') { ->
    assert ResponseStorage.getResponse().statusCode == HttpStatus.SC_FORBIDDEN
}

Then(~'^access unauthorized$') { ->
    assert ResponseStorage.getResponse().statusCode == HttpStatus.SC_UNAUTHORIZED
}

Then(~'^bad request$') { ->
    assert ResponseStorage.getResponse().statusCode == HttpStatus.SC_BAD_REQUEST
}

Then(~'^text response should be "([^"]*)"$') { String expectedResponse ->
    def response = ResponseStorage.getResponse().body.print()
    assert response == expectedResponse
}

Then(~'^response should contain$') { DataTable propertiesTable ->
    def response = ResponseStorage.getResponse().body.as(Map)
    def expectedData = propertiesTable.asMaps(String, String)

    expectedData.each { row ->
        assertValues(response, row)
    }

}
