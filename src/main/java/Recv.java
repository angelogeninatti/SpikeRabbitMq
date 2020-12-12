import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.49.2");
        factory.setPort(30672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //Dichiaro anche qui la coda: in questo modo, anche se il ricevente inizia prima del mittente,
        //Funziona comunque tutto
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, new CancelCallback() {
            public void handle(String consumerTag) throws IOException {
            }
        });
        //Non ho usato le lambda come nell'esempio perché Java 8, che stiamo usando nel progetto,
        //Non le supporta.
    }
}