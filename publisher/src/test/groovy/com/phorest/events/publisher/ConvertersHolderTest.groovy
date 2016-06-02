package com.phorest.events.publisher

import com.phorest.events.model.EventSource
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ConvertersHolderTest extends Specification {

  def "should throw an Exception if there are no converters defined"() {
    given:
    ConvertersHolder convertersHolder = new ConvertersHolder(converters: [])

    when:
    convertersHolder.init()

    then:
    thrown(IllegalArgumentException)
  }

  def "should provide expected converter"() {
    given:
    ConvertersHolder convertersHolder = new ConvertersHolder(converters: converters)
    convertersHolder.init()

    when:
    EventSourceToEventConverter<EventSource> converter = convertersHolder.getConverter(eventSource)


    then:
    converter.supportedType == eventSource

    where:
    converters                                                            | eventSource
    [createConverter(EventSource), createConverter(ConvertersHolderTest)] | ConvertersHolderTest
  }

  private EventSourceToEventConverter<EventSource> createConverter(Class<?> type) {
    EventSourceToEventConverter<EventSource> converter = Mock(EventSourceToEventConverter)
    _ * converter.getSupportedType() >> type
    converter
  }
}
