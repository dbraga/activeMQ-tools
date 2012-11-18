import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * User: dbraga
 */

public class Producer {

    static String activeMqBrokerUrl = "failover:(tcp://localhost:61616)";
    static String topicName = "topic_test_damiano";
    private static final Logger LOG = Logger.getLogger(Producer.class);
    private static Session session ;
    private static Connection connection;
    private static MessageProducer producer;

    public Producer(){
        Shared shared = new Shared(activeMqBrokerUrl);
        session = shared.getSession();
        connection = shared.getConnection();

        try{
            Destination destination = session.createTopic(topicName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

//            producer.setTimeToLive(2000);
            connection.start();
        } catch (Exception e){
           LOG.error(e);
        }


    }

    public static void main(String args[]) throws JMSException, InterruptedException {
        Producer p = new Producer();
        int count = 0 ;
        while (true){
            count ++;
            String messageBody = "This is a fake body message "+  count;
            TextMessage textMessage = session.createTextMessage(messageBody);
            producer.send(textMessage);
            LOG.info("Send message "+messageBody);
            // Wait 5 seconds
            Thread.sleep(5000);
        }
    }

}
