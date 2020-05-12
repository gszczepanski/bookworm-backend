package org.bookworm.library.controllers;

import org.bookworm.library.entities.Book;
import org.bookworm.library.entities.BookStatus;
import org.bookworm.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/19
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookRepository bookRepository;

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

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Book> findAll(Pageable pageable) {

        return bookRepository.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") UUID id) {

        bookRepository.deleteById(id);
        return ResponseEntity.ok("book deleted");
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}/status/{status}", method = RequestMethod.PATCH)
    public ResponseEntity<?> setStatus(@PathVariable(value = "status") BookStatus status,
                                     @PathVariable(value = "id") UUID id) {

        bookRepository.setStatus(status, id);
        return ResponseEntity.ok("book type updated");
    }
}
