package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.BookDto;
import org.bookworm.library.entities.Book;
import org.bookworm.library.entities.BookStatus;
import org.bookworm.library.mappers.BookMapper;
import org.bookworm.library.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Book save(BookDto bookDto) {
        return bookRepository.save(bookMapper.toBook(bookDto));
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    public void setStatus(BookStatus status, UUID id) {
        bookRepository.setStatus(status, id);
    }
}
