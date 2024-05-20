package com.interview.coding.battleshipservice.service;

import com.interview.coding.battleshipservice.exception.KafkaProducerException;
import com.interview.coding.battleshipapi.event.GameCreatedEvent;
import com.interview.coding.battleshipapi.event.GameFireEvent;
import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import javax.resource.ResourceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class KafkaProducerServiceTest {

    @InjectMocks
    KafkaProducerService kafkaProducerService;

    @Mock
    KafkaConnectionFactory kafkaConnectionFactory;

    @Mock
    KafkaConnection kafkaConnection;

    @BeforeClass
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void tearDown() {
        Mockito.reset(kafkaConnection);
        Mockito.reset(kafkaConnectionFactory);
    }

    @Test()
    public void testGameCreatedEvent() throws ResourceException {
        Mockito.when(kafkaConnectionFactory.createConnection()).thenReturn(kafkaConnection);
        kafkaProducerService.setFactory(kafkaConnectionFactory);
        kafkaProducerService.publish(new GameCreatedEvent());
        Mockito.verify(kafkaConnection, Mockito.times(1)).send(Mockito.any());
    }

    @Test()
    public void testGameFireEvent() throws ResourceException {
        Mockito.when(kafkaConnectionFactory.createConnection()).thenReturn(kafkaConnection);
        kafkaProducerService.setFactory(kafkaConnectionFactory);
        kafkaProducerService.publish(new GameFireEvent());
        Mockito.verify(kafkaConnection, Mockito.times(1)).send(Mockito.any());
    }


    @Test(expectedExceptions = KafkaProducerException.class)
    public void testGameCreatedEventException() throws ResourceException {
        Mockito.when(kafkaConnectionFactory.createConnection()).thenThrow(new ResourceException("not connected"));
        kafkaProducerService.setFactory(kafkaConnectionFactory);
        kafkaProducerService.publish(new GameCreatedEvent());
    }

    @Test(expectedExceptions = KafkaProducerException.class)
    public void testGameFireEventException() throws ResourceException {
        Mockito.when(kafkaConnectionFactory.createConnection()).thenThrow(new ResourceException("not connected"));
        kafkaProducerService.setFactory(kafkaConnectionFactory);
        kafkaProducerService.publish(new GameFireEvent());
    }
}
