package com.example.kafka.producer;

import com.example.kafka.common.MessageEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class SimpleProducer {
    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, MessageEntity> kafkaTemplate;

    public void send(String topic,MessageEntity message){
        kafkaTemplate.send(topic,message);
    }
    public void send(String topic,String key,MessageEntity message){
        ProducerRecord<String,MessageEntity> record = new ProducerRecord<>(
                topic,
                key,
                message
        );
        long startTime = System.currentTimeMillis();
        ListenableFuture<SendResult<String,MessageEntity>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime,key,message));
    }
}