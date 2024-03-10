package com.chat.app.Controller;

import com.chat.app.Model.ChatHistory;
import com.chat.app.Model.Message;
import com.chat.app.Model.User;
import com.chat.app.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final String ANALYTICS_COLLECTION = "message";

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/addMessage")
    public void addMessage(@RequestBody Message message){
        System.out.println("Before saving");
        // Chat message test
//        mongoTemplate.insert(message, ANALYTICS_COLLECTION);

        // Chat History test
//        ChatHistory chatHistory = new ChatHistory();
//        chatHistory.setChannelId(message.getChannelId());
//        chatHistory.setMessages(List.of(message));
//
//        mongoTemplate.insert(chatHistory, "chat_history");

        System.out.println("After Saving");
    }

    @PostMapping("/addUserToSQL")
    public void addMessageToSQL(@RequestBody User user){
        System.out.println("Before saving");
        userRepo.save(user);
        System.out.println("After Saving");
    }
}
