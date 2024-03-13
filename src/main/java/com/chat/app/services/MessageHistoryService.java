package com.chat.app.services;

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
            // Handle the case where the channelId doesn't exist
            throw new IllegalArgumentException("ChannelId '" + channelId + "' does not exist in Chat_History");
        }

        // Define update operation to append message to the messages array
        Update update = new Update().push("messages", message);

        // Execute update operation
        mongoTemplate.updateFirst(query, update, ChatConstants.CHAT_HISTORY);

    }

    public List<Message> getMessageHistory(String channelId) {
        // Define query to retrieve messages for the given channel_id
        Query query = new Query(Criteria.where("channel_id").is(channelId));

        // Execute query to fetch messages
        List<Message> messages = mongoTemplate.find(query, Message.class, "Chat_History");

        // Check if messages exist for the given channel_id
        if (messages.isEmpty()) {
            throw new IllegalArgumentException("No messages for the given channel_id");
        } else {
            return messages;
        }
    }
}
