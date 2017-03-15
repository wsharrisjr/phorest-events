package acceptance.steps.common

import com.jayway.restassured.response.Response


class ResponseStorage {

  static Response response

  static void storeResponse(Response responseToStore){
    response = responseToStore
  }

  static Response getResponse(){
    response
  }

  static void clearResponse() {
    response = null
  }

}
