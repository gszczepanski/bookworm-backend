package org.bookworm.library.controllers;

import org.bookworm.library.entities.Author;
import org.bookworm.library.repositories.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    IAuthorRepository authorRepository;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Author insert(@RequestBody Author author) {

        return authorRepository.save(author);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Author update(@PathVariable(value = "id") UUID id, @RequestBody Author author) {

        return authorRepository.save(author);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Author> findAll(Pageable pageable) {

        return authorRepository.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") UUID id) {

        authorRepository.deleteById(id);
        return ResponseEntity.ok("author deleted");
    }
}
