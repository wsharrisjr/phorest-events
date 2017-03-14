package acceptance.common

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

static ObjectMapper getObjectMapper() {
    ObjectMapper om = new ObjectMapper()
    om.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    om.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
    om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    om
}
