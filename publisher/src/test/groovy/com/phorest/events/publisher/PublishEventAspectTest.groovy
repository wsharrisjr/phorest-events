package com.phorest.events.publisher

import com.phorest.events.model.EventSource
import spock.lang.Specification

class PublishEventAspectTest extends Specification {

  def "should publish event"() {
    given:
    DefaultEventPublisher eventPublisher = Mock(DefaultEventPublisher)
    PublishEventAspect publishEventAspect = new PublishEventAspect(eventPublisher: eventPublisher)

    and:
    PublishEvent publishEvent = Mock(PublishEvent)
    EventSource eventSource = Stub(EventSource)

    and:
    String expectedExchange = 'exchange'
    String expectedRoutingKey = 'routingKey'

    when:
    publishEventAspect.publishEvent(publishEvent, eventSource)

    then:
    1 * publishEvent.exchange() >> expectedExchange
    1 * publishEvent.routingKey() >> expectedRoutingKey
    1 * eventPublisher.publishEvent(expectedExchange, expectedRoutingKey, eventSource)
  }
}
