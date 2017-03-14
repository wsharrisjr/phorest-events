package com.phorest.events.testapp

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class EventPublishController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(method = RequestMethod.POST)
    void publishEvent(@RequestBody String body) {
        rabbitTemplate.convertAndSend('test', 'test', body)
    }
}
