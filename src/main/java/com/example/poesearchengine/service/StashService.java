package com.example.poesearchengine.service;

import com.example.poesearchengine.model.CollectionNames;
import com.example.poesearchengine.repository.StashRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Alikin E.A. on 2019-06-04.
 */
@Service
public class StashService {

    private static final String STASH_URL = "http://api.pathofexile.com/public-stash-tabs/?id=";

    @Autowired
    StashRepository repository;

    public int updateStashData() {
        repository.dropCollection();

        var countStash = 0;
        var batchId = "6952643-7480019-6843867-8038403-7372070";
        for (int i = 0; i < 1000; i++) {
            var document = updateStashBatch(batchId);
            batchId = (String)document.get("next_change_id");
            if (batchId == null || batchId.isEmpty() || "0".equals(batchId)) {
                break;
            }
            var list = (List<Document>) document.get(CollectionNames.STASHES);
            repository.insertData(list);
            System.out.println("Insert element count : " + list.size() + " count query : " + i);

            countStash = countStash + list.size();
        }

        return countStash;
    }

    private Document updateStashBatch(String batchId) {
        System.out.println("BatchId = " + batchId);

        var restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity( STASH_URL + batchId, String.class);
        return Document.parse(response.getBody());
    }

    public List<Document> searchItem(String name) {
        var documents = repository.searchItem(name);

        //sorry for this
        documents.forEach(stash -> {
            List<Document> items = (List<Document>)stash.get("items");
            stash.put("items",items.stream().filter(item -> ((String)item.get("name")).contains(name)));
        });
        return documents;
    }
}
