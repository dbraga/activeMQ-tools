import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.Connection;
import javax.jms.Session;

/**
 * User: dbraga
 */
public class Shared {

  private static final Logger LOG = Logger.getLogger(Shared.class);
  private Connection connection;
  private Session session;


  public Shared(String brokerUrl){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
      try{
          this.connection = connectionFactory.createConnection();
          this.session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
      } catch (Exception e){
          LOG.error(e);
      }

    }

    public Session getSession() {
        return session;
    }

    public Connection getConnection() {
        return connection;
    }

}
