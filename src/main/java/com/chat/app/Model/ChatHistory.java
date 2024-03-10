package com.chat.app.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "chat_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory {
    @Id
    private String id;
    private String channelId;
    private List<Message> messages;
}