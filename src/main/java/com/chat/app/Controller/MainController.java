package com.chat.app.Controller;

import com.chat.app.Model.Message;
import com.chat.app.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    MessageRepo messageRepo;

    @PostMapping("/addMessage")
    public void addMessage(@RequestBody Message message){
        System.out.println("Before saving");
        messageRepo.save(message);
        System.out.println("After Saving");
    }
}
