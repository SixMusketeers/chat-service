package com.chat.app.Controller;

import com.chat.app.Model.Message;
import com.chat.app.Model.User;
import com.chat.app.Repository.UserRepo;
import com.chat.app.config.ChatConstants;
//import com.chat.app.services.KafkaService;
import com.chat.app.services.MessageHistoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@NoArgsConstructor
@AllArgsConstructor
public class MainController {

    @Autowired
    MessageHistoryService messageHistoryService;

    @Autowired
    MongoTemplate mongoTemplate;

//    @Autowired
//    KafkaService kafkaService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private UserRepo userRepo;


    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody Message message) {

        try {
            // More validation if needed
            if (Objects.isNull(message.getChannelId()) || message.getChannelId().isEmpty()) {
                throw new IllegalArgumentException("Channel ID cannot be empty");
            }

            // Store message in message document
            message = mongoTemplate.insert(message, ChatConstants.MESSAGE);
            logger.info("*** Message Sent successfully *** " + message.getId());

            // Send message to kafka
//            kafkaService.addMessageToQueue(message);

//            Store message in chat history document
            messageHistoryService.persistMessageHistory(message);
            logger.info("*** Message Received successfully ***" + message.getId());

        } catch (IllegalStateException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return "Internal Server Error";
        }
        return "Message Sent successfully";
    }

    @GetMapping("/show-messages")
    public List<Message> showMessages(@RequestParam String channelId) {
        try{
            return messageHistoryService.getMessageHistory(channelId);
        } catch (IllegalArgumentException ex) {
            logger.error("Error showing messages" + ex.getMessage());
            return null;
        } catch (Exception ex) {
            logger.error("Internal server error");
            return null;
        }
    }



    @PostMapping("/addUserToSQL")
    public void addMessageToSQL(@RequestBody User user){
        System.out.println("Before saving");
        userRepo.save(user);
        System.out.println("After Saving");
    }
}