package order.processor

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

class App {
    init {
        val consumer = createConsumer()
        consumer.subscribe(listOf("orders"))

        while(true) {
            val records = consumer.poll(Duration.ofSeconds(1))
            records.forEach {
                println(it.value())
            }
        }
    }

    fun createConsumer(): KafkaConsumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = System.getenv("KAFKA_BROKER") ?: "kafka:9092"
        props["group.id"] = "order-processor"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java
        return KafkaConsumer(props)
    }
}

fun main(args: Array<String>) {
    App()
}
