package org.bookworm.library.controllers;

import org.bookworm.library.entities.Book;
import org.bookworm.library.repositories.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/19
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    IBookRepository bookRepository;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Book insert(@RequestBody Book book) {

        return bookRepository.save(book);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Book update(@PathVariable(value = "id") UUID id, @RequestBody Book book) {

        return bookRepository.save(book);
    }

}
