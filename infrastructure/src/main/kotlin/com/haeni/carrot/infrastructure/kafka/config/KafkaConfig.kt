package com.haeni.carrot.infrastructure.kafka.config

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfig {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String  // api 모듈의 yml에서 주입됨

    companion object {
        const val ORDER_EVENTS_TOPIC = "order-events"
        const val ORDER_EVENTS_DLT = "order-events.DLT"
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun orderEventsTopic(): NewTopic =
        TopicBuilder.name(ORDER_EVENTS_TOPIC)
            .partitions(3)
            .replicas(1)
            .build()

    @Bean
    fun orderEventsDlt(): NewTopic =
        TopicBuilder.name(ORDER_EVENTS_DLT)
            .partitions(1)
            .replicas(1)
            .build()
}