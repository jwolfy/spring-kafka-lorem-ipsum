package jwolfy.config;

import jwolfy.domain.CalculationResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${kafka.bootstrap_servers}")
    private String bootstrapServers;

    @Value("${kafka.group_id}")
    private String groupId;

    @Value("${kafka.number_of_consumers}")
    private Integer numOfConsumers;

    @Bean
    public ConsumerFactory<String, CalculationResult> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG, groupId,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "*"
        ));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CalculationResult> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CalculationResult> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(numOfConsumers);
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(1000L);
        return factory;
    }
}
