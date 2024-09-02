//package com.chat.app.services;
//
//
//import com.chat.app.Model.Message;
//import com.chat.app.config.ChatConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class KafkaService {
//
//    @Autowired
//    KafkaTemplate kafkaTemplate;
//
//    @Autowired
//    MessageHistoryService messageHistoryService;
//
//    public void addMessageToQueue(Message message) {
//        kafkaTemplate.send(ChatConstants.TOPIC_MESSAGE_QUEUE, message);
//    }
//
//    @KafkaListener(topics = ChatConstants.TOPIC_MESSAGE_QUEUE)
//    public void receiveMessage(Message message) {
//        // Process the received message (e.g., insert into chat_history)
//        messageHistoryService.persistMessageHistory(message);
//    }
//
//}
