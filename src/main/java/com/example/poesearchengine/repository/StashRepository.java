package com.example.poesearchengine.repository;

import com.example.poesearchengine.model.CollectionNames;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alikin E.A. on 2019-06-03.
 */
@Repository
public class StashRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Document> searchItem(String name) {
        return mongoTemplate.getCollection(CollectionNames.STASHES)
                .find(Document.parse("{'items.name' :/" + name + "/}"))
                .limit(1)
                .into(new ArrayList<>());
    }

    public void insertData(List<? extends Document> documents) {
        mongoTemplate.getCollection(CollectionNames.STASHES)
                .insertMany(documents);
    }

    public void dropCollection() {
        mongoTemplate.getCollection(CollectionNames.STASHES).drop();
    }
}
