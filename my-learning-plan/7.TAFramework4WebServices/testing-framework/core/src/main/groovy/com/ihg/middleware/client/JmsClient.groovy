package com.ihg.middleware.client

import groovy.util.logging.Log4j
import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.command.ActiveMQQueue
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator

import javax.jms.TextMessage
import java.util.concurrent.TimeUnit

/**
 * Custom class for call services using JMS.
 * Based on @see org.springframework.jms.core.JmsTemplate.
 *
 * @author ilya.lapitan@ihg.com
 */
@Log4j
class JmsClient {

    /**
     * Message broker connection URL.
     */
    String brokerUrl

    /**
     * Queue name for request message.
     */
    String requestQueueName

    /**
     * Queue name for response message.
     */
    String responseQueueName

    /**
     * Configure JMSClient for work with JMS service.
     * @param brokerUrl - message broker connection URL.
     * @param requestQueueName - queue name for request messages.
     * @param responseQueueName - queue name for response messages.
     */
    JmsClient(String brokerUrl, String requestQueueName, String responseQueueName) {
        this.brokerUrl = brokerUrl
        this.requestQueueName = requestQueueName
        this.responseQueueName = responseQueueName
    }

    /**
     * Send message to service and receive response message from the service.
     * @param requestMessage - message for sending to service
     * @return String response message from the service
     */
    def sendAndReceive(String requestMessage){
        // configure JMS template
        def jmsTemplate = new JmsTemplate(new ActiveMQConnectionFactory(brokerUrl))
        jmsTemplate.receiveTimeout = TimeUnit.SECONDS.toMillis(5)

        // configure response and request queues
        def requestQueue = new ActiveMQQueue(requestQueueName)
        def responseQueue = new ActiveMQQueue(responseQueueName)

        log.debug "Sending message to ${brokerUrl}:\n ${requestMessage}"

        // setup reply queue and send request message to queue
        jmsTemplate.send(requestQueue, { session ->
            def message = session.createTextMessage(requestMessage)
            message.JMSReplyTo = responseQueue
            message
        } as MessageCreator)

        // receive response message from queue
        def responseMessage  = jmsTemplate.receive(responseQueue) as TextMessage

        def text = responseMessage?.getText()

        //using it for debug
        log.debug "Response from ${brokerUrl} is :\n${text}"

        text
    }
}
