package com.chat.app.services;

import com.chat.app.Model.ChatHistory;
import com.chat.app.Model.Message;
import com.chat.app.config.ChatConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@NoArgsConstructor
@AllArgsConstructor
public class MessageHistoryService {

    @Autowired
    MongoTemplate mongoTemplate;

    public void persistMessageHistory(Message message) {

        String channelId = message.getChannelId();

        Query query = new Query(Criteria.where("channel_id").is(channelId));

        // Check if the document with the specified channelId exists
        if (!mongoTemplate.exists(query, ChatConstants.CHAT_HISTORY)) {
            // create a new channel
             mongoTemplate.insert(new ChatHistory(channelId, List.of(message)), ChatConstants.CHAT_HISTORY);
        } else {
            // Define update operation to append message to the messages array
            Update update = new Update().push("messages", message);

            // Execute update operation
            mongoTemplate.updateFirst(query, update, ChatConstants.CHAT_HISTORY);
        }



    }

    public List<Message> getMessageHistory(String channelId) {
        Query query = new Query(Criteria.where(ChatConstants.CHANNEL_ID).is(channelId));

        // Execute query to fetch messages
        ChatHistory chatHistory = mongoTemplate.findOne(query, ChatHistory.class, ChatConstants.CHAT_HISTORY);

//         Check if messages exist for the given channel_id
        if (Objects.isNull(chatHistory)) {
            throw new IllegalArgumentException("Chat Histroy Error");
        } else if (chatHistory.getMessages() == null || chatHistory.getMessages().isEmpty()) {
            throw new IllegalArgumentException("No messages for the given channel_id");
        } else {
            return chatHistory.getMessages();
        }
    }
}
