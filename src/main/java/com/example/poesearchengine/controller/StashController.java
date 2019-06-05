package com.example.poesearchengine.controller;

import com.example.poesearchengine.repository.StashRepository;
import com.example.poesearchengine.service.StashService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by Alikin E.A. on 2019-06-03.
 */
@RestController
@RequestMapping(value = "/stash")
public class StashController {

    @Autowired
    StashService service;

    @Autowired
    StashRepository repository;

    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public List<Document> search(@PathVariable String name) {
        return service.searchItem(name);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public int update() {
        return service.updateStashData();
    }
}
