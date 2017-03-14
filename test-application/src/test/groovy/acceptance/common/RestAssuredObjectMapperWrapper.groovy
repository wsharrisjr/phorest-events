package acceptance.common

import com.jayway.restassured.mapper.ObjectMapper
import com.jayway.restassured.mapper.ObjectMapperDeserializationContext
import com.jayway.restassured.mapper.ObjectMapperSerializationContext


class RestAssuredObjectMapperWrapper implements ObjectMapper {

  com.fasterxml.jackson.databind.ObjectMapper mapper

  @Override
  public Object deserialize(ObjectMapperDeserializationContext context) {
    try {
      return mapper.readValue(context.getDataToDeserialize().asString(), mapper.constructType(context.getType()))
    } catch (IOException e) {
      throw new IllegalArgumentException("Error deserializing response", e)
    }
  }

  @Override
  public Object serialize(ObjectMapperSerializationContext context) {
    try {
      Object object = context.getObjectToSerialize()
      ByteArrayOutputStream stream = new ByteArrayOutputStream()
      mapper.writeValue(mapper.getFactory().createGenerator(stream), object)
      return stream.toString()
    } catch (IOException e) {
      throw new IllegalArgumentException("Error serializing object", e)
    }
  }
}
