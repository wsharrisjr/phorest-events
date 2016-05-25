package com.phorest.events.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import spock.lang.Unroll

class ItemTypeTest extends Specification {

  ObjectMapper objectMapper = new ObjectMapper()

  @Unroll
  def "should deserialize item type"() {
    when:
    ItemTypeWrapper result = objectMapper.readValue(itemTypeJson(itemType.name()), ItemTypeWrapper)

    then:
    result.itemType == itemType

    where:
    itemType << ItemType.values().toList()
  }

  @Unroll
  def "should default to UNKNOWN when deserializing not recognized item type"() {
    when:
    ItemTypeWrapper result = objectMapper.readValue(itemTypeJson('unrecognized'), ItemTypeWrapper)

    then:
    result.itemType == ItemType.UNKNOWN
  }

  private static String itemTypeJson(String itemType) {
    """{"itemType": "${itemType}"}"""
  }

  private static class ItemTypeWrapper {
    ItemType itemType
  }
}
