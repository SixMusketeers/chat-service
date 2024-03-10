package com.chat.app.Repository;

import com.chat.app.Model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepo extends MongoRepository<Message, Integer> {
}
