package org.bookworm.library.controllers;

import org.bookworm.library.entities.Publisher;
import org.bookworm.library.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Publisher insert(@RequestBody Publisher publisher) {

        return publisherRepository.save(publisher);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Publisher update(@PathVariable(value = "id") Integer id, @RequestBody Publisher publisher) {

        return publisherRepository.save(publisher);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Publisher> findAll(Pageable pageable) {

        return publisherRepository.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") Integer id) {

        publisherRepository.deleteById(id);
        return ResponseEntity.ok("publisher deleted");
    }
}
