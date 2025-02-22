package com.interview.coding.battleshipservice.service;

import com.interview.coding.battleshipservice.exception.KafkaProducerException;
import com.interview.coding.battleshipapi.event.GameCreatedEvent;
import com.interview.coding.battleshipapi.event.GameFireEvent;
import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Resource;
import javax.inject.Singleton;

@Singleton
public class KafkaProducerService {

    private KafkaConnectionFactory factory;

    public void publish(GameCreatedEvent event) {
        try (KafkaConnection conn = factory.createConnection()) {
            conn.send(new ProducerRecord("battleship.game.new", event.json()));
        } catch (Exception e) {
            throw new KafkaProducerException(e);
        }
    }

    public void publish(GameFireEvent event) {
        try (KafkaConnection conn = factory.createConnection()) {
            conn.send(new ProducerRecord("battleship.game.field.fire", event.json()));
        } catch (Exception e) {
            throw new KafkaProducerException(e);
        }
    }

    @Resource(lookup="java:/KafkaConnectionFactory")
    public void setFactory(KafkaConnectionFactory kafkaConnectionFactory) {
        this.factory = kafkaConnectionFactory;
    }
}
