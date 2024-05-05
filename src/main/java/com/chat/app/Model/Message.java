package com.chat.app.Model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    private String id;
    private String channelId;
    private String userId;
    private String content;
}
