package com.phorest.events.publisher

import com.phorest.events.model.EventSource
import spock.lang.Specification
import spock.lang.Unroll

class RoutingKeyResolverTest extends Specification {

  @Unroll
  def "should resolve routing key"() {
    given:
    EventSource eventSource = Mock(EventSource)
    _ * eventSource.routingKeyArgs >> routingKeyArgs

    when:
    String routingKey = RoutingKeyResolver.resolve(template, eventSource)

    then:
    routingKey == expectedRoutingKey

    where:
    template                      | routingKeyArgs     | expectedRoutingKey
    'withoutVariables'            | []                 | 'withoutVariables'
    'withoutVariables'            | ['var']            | 'withoutVariables'
    'with variable: %s'           | ['var']            | 'with variable: var'
    'with variable: %s'           | ['var', 'skip me'] | 'with variable: var'
    'with many variables: %s, %s' | ['var1', 'var2']   | 'with many variables: var1, var2'
  }
}
