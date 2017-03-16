# Phorest Events

Defining the Phorest Cloud events model.

## v.1.1.8
- fixed object mapper for rabbit connection factory to use joda time module
- removed unsed beans

## v.1.1.7
- added DelayedRetryMessageRecoverer
- use the following properties to customize retry policy:
    * `phorest.events.configuration.rabbit.retry.minDelay`
    * `phorest.events.configuration.rabbit.retry.maxDelay`
    * `phorest.events.configuration.rabbit.retry.backoffBase`
    * `phorest.events.configuration.rabbit.retry.maxRetryAttempts`
- added max concurrent consumers property:
    * `phorest.events.configuration.rabbit.maxConcurrentConsumers`
- increased version of `org.springframework.amqp:spring-rabbit:1.7.1.RELEASE'
    
To use delayed retry policy your Exchange must have 'delayed=true' property 