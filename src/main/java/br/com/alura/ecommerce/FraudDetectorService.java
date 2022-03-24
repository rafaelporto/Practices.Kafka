package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;
import java.util.Properties;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try(var service = new KafkaService<Order>(FraudDetectorService.class.getName(),"ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class,
                Map.of())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String,Order> record)
    {
        System.out.println("----------------------------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            //ignoring
            e.printStackTrace();
        }
        System.out.println("Order processed");
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getName());

        return properties;
    }
}
