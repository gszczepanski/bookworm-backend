package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookworm.library.dto.AuthorDto;
import org.bookworm.library.dto.BookForDisplayDto;
import org.bookworm.library.dto.BookForModificationDto;
import org.bookworm.library.entities.BookStatus;
import org.bookworm.library.security.roles.AllowedRoles;
import org.bookworm.library.services.AuthorService;
import org.bookworm.library.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/19
 */
@RequiredArgsConstructor
@CrossOrigin(origins = "${ws.cross.origin.address}")
@RestController
@RequestMapping(value = {"/books", "/v1/books"})
@Slf4j
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;

    @PostMapping(value = "")
    @AllowedRoles("CLIENT")
    public ResponseEntity<BookForModificationDto> insert(@RequestBody BookForModificationDto bookDto) {
        log.info("Inserting book {}", bookDto);
        BookForModificationDto book = bookService.create(bookDto);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<BookForModificationDto> update(@RequestBody BookForModificationDto bookDto) {
        log.info("Updating book {}", bookDto);
        Optional<BookForModificationDto> optionalBookForModificationDto = bookService.update(bookDto);
        if (optionalBookForModificationDto.isPresent()) {
            return new ResponseEntity<>(optionalBookForModificationDto.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<BookForModificationDto> updatePart(@RequestBody BookForModificationDto bookDto) {
        log.info("Updating partially book {}", bookDto);
        Optional<BookForModificationDto> optionalBookForModificationDto = bookService.updatePart(bookDto);
        if (optionalBookForModificationDto.isPresent()) {
            return new ResponseEntity<>(optionalBookForModificationDto.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "")
    @AllowedRoles("CLIENT")
    public ResponseEntity<Page<BookForDisplayDto>> findAll(Pageable pageable) {
        log.info("Find all books for {}", pageable);
        Page<BookForDisplayDto> bookDtos = bookService.findAll(pageable);
        if (!bookDtos.isEmpty()) {
            for (BookForDisplayDto bookDto : bookDtos) {
                List<AuthorDto> authorDtos = authorService.findAllByBookId(bookDto.getId());
                bookDto.setAuthorDtoList(authorDtos);
            }
            return new ResponseEntity<>(bookDtos, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}")
    @AllowedRoles("CLIENT")
    public ResponseEntity<BookForDisplayDto> findById(@PathVariable(value = "id") UUID id) {
        log.info("Searching for book with id {}", id);
        Optional<BookForDisplayDto> bookDtoOptional = bookService.findById(id);
        if (bookDtoOptional.isPresent()) {
            List<AuthorDto> authorDtos = authorService.findAllByBookId(bookDtoOptional.get().getId());
            bookDtoOptional.get().setAuthorDtoList(authorDtos);
            return new ResponseEntity<>(bookDtoOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{bookId}/author/{authorId}")
    @AllowedRoles("EDITOR")
    public ResponseEntity<String> addAuthorToBook(@PathVariable(value = "bookId") UUID bookId,
                                                  @PathVariable(value = "authorId") UUID authorId) {
        log.info("Adding author with id {} to book with id {}", authorId, bookId);
        bookService.addAuthorToBook(bookId, authorId);
        return ResponseEntity.ok("author added to book");
    }

    @DeleteMapping(value = "/{id}")
    @AllowedRoles("EDITOR")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {
        log.info("Deleting book with id {}", id);
        bookService.deleteById(id);
        return ResponseEntity.ok("book deleted");
    }

    @PatchMapping(value = "/{id}/status/{status}")
    @AllowedRoles("EDITOR")
    public ResponseEntity<String> setStatus(@PathVariable(value = "status") BookStatus status,
                                            @PathVariable(value = "id") UUID id) {
        log.info("Setting status {} for book with id {}", status, id);
        bookService.setStatus(status, id);
        return ResponseEntity.ok("book type updated");
    }
}
