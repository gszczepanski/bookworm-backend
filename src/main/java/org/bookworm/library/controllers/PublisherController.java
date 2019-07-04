package org.bookworm.library.controllers;

import org.bookworm.library.entities.Publisher;
import org.bookworm.library.repositories.IPublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    IPublisherRepository publisherRepository;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Publisher insert(@RequestBody Publisher publisher) {

        return publisherRepository.save(publisher);
    }

}
