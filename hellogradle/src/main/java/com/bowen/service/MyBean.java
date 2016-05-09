package com.bowen.service;

import com.mongodb.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

/**
 * Created by bcl on 16/5/9.
 */
@Component
public class MyBean {
    private final MongoDbFactory mongo;

    @Autowired

    public MyBean(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    public void example() {
        DB db = mongo.getDb();
    }
}
