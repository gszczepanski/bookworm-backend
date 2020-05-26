package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.BookDto;
import org.bookworm.library.entities.Book;
import org.bookworm.library.entities.BookStatus;
import org.bookworm.library.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/19
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PostMapping(value = "")
    public Book insert(@RequestBody BookDto bookDto) {

        return bookService.save(bookDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PutMapping(value = "/{id}")
    public Book update(@PathVariable(value = "id") UUID id, @RequestBody BookDto bookDto) {

        return bookService.save(bookDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @GetMapping(value = "")
    public Page<Book> findAll(Pageable pageable) {

        return bookService.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {

        bookService.deleteById(id);
        return ResponseEntity.ok("book deleted");
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PatchMapping(value = "/{id}/status/{status}")
    public ResponseEntity<String> setStatus(@PathVariable(value = "status") BookStatus status,
                                     @PathVariable(value = "id") UUID id) {

        bookService.setStatus(status, id);
        return ResponseEntity.ok("book type updated");
    }
}
