import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * User: dbraga
 */
public class Consumer implements MessageListener {
    private static final Logger LOG = Logger.getLogger(Consumer.class);
    static String activeMqBrokerUrl = "failover:(tcp://localhost:61616)";
    static String topicName = "topic_test_damiano";

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        LOG.info("received message " + textMessage);
        try {
            LOG.info("text: " + textMessage.getText());
        } catch (JMSException e) {
            LOG.error("", e);
        }
    }


    public static void listenToTopic(String brokerUrl, String topicName, MessageListener listener) throws JMSException {
        LOG.info("creating topic listener");
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("unique_client_id_123");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic dest = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableSubscriber(dest, "this_is_a_unique_id_12345");
        consumer.setMessageListener(listener);
        connection.start();
    }

    public static void main(String[] args) throws JMSException {
        MessageListener consumer = new Consumer();
        Consumer.listenToTopic(activeMqBrokerUrl, topicName,consumer);

    }

}
