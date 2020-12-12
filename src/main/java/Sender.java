import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    private final static String QUEUE_NAME = "hello";
    public static void main(String args[]) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.49.2"); //minikube ip. Quando lavoreremo per il progetto, basterà usare rabbitmq
        factory.setPort(30672);
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        finally{
            //A differenza dell'esempio sul sito di rabbitmq,
            //Uso il finally con dentro le close esplicite
            //Perché stiamo usando Java 8 che non supporta quel tipo di costrutto.
            if(channel != null)
                channel.close();
            if(connection != null)
                connection.close();
        }
    }
}
