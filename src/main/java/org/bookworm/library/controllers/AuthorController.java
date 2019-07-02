package org.bookworm.library.controllers;

import org.bookworm.library.entities.Author;
import org.bookworm.library.repositories.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
