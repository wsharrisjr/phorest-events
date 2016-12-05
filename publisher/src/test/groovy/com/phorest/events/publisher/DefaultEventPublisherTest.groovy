package com.phorest.events.publisher

import com.phorest.events.model.Event
import com.phorest.events.model.EventData
import com.phorest.events.model.EventSource
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.rabbit.core.RabbitOperations
import spock.lang.Specification

class DefaultEventPublisherTest extends Specification {

  ConvertersHolder convertersHolder = Mock(ConvertersHolder)
  RabbitOperations rabbitOperations = Mock(RabbitOperations)
  DefaultEventPublisher eventPublisher = new DefaultEventPublisher(convertersHolder: convertersHolder, rabbitOperations: rabbitOperations)

  def "should throw an exception if there is no converter defined"() {
    given:
    String exchange = 'exchange'
    String routingTemplate = 'routingTemplate'
    EventSource eventSource = Mock(EventSource)
    _ * eventSource.routingKeyArgs >> []

    when:
    eventPublisher.publishEvent(exchange, routingTemplate, eventSource)

    then:
    1 * convertersHolder.getConverter(_ as Class<?>) >> null
    0 * rabbitOperations.convertAndSend(exchange, routingTemplate, _)
    thrown(IllegalArgumentException)
  }

  def "should convert event source into event data and send it to expected routing key"() {
    given:
    String exchange = 'exchange'
    String routingTemplate = 'routingTemplate-%s'
    EventSource eventSource = Mock(EventSource)
    _ * eventSource.routingKeyArgs >> ['arg']

    and:
    Event<EventData> event = Stub(Event)
    EventSourceToEventConverter<EventSource> converter = Mock(EventSourceToEventConverter)

    when:
    eventPublisher.publishEvent(exchange, routingTemplate, eventSource)

    then:
    1 * convertersHolder.getConverter(_ as Class<?>) >> converter
    1 * converter.convert(eventSource) >> event
    1 * rabbitOperations.convertAndSend(exchange, 'routingTemplate-arg', event, _ as MessagePostProcessor)
  }
}
