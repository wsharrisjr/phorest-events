package acceptance.steps

import acceptance.steps.common.ResponseStorage
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks

import static acceptance.common.BaseApiClient.givenApiClient
import static cucumber.api.groovy.Hooks.Before

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

Before() {
    ResponseStorage.clearResponse()
    def response = givenApiClient().delete("/messagesCount")
    assert response.statusCode == 200
}

When(~'^message "([^"]+)" is sent$') { String message ->
    def response = givenApiClient().body(message).post('/')
    ResponseStorage.storeResponse(response)
}

When(~'^wait "([^"]+)" seconds$') { int seconds ->
    Thread.sleep(seconds * 1000)
}

When(~'^request messages count') { ->
    def response = givenApiClient().get('/messagesCount')
    ResponseStorage.storeResponse(response)
}