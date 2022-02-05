package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.BookForDisplayDto;
import org.bookworm.library.dto.BookForModificationDto;
import org.bookworm.library.dto.BookMapper;
import org.bookworm.library.dto.BookPatchMapper;
import org.bookworm.library.entities.Author;
import org.bookworm.library.entities.Book;
import org.bookworm.library.entities.BookStatus;
import org.bookworm.library.repositories.AuthorRepository;
import org.bookworm.library.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final BookPatchMapper bookPatchMapper;


    public BookForModificationDto create(BookForModificationDto bookDto) {

        return bookMapper.toForModificationDto(
                bookRepository.saveAndFlush(bookMapper.toBook(bookDto))
        );
    }

    public Optional<BookForModificationDto> update(BookForModificationDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getId());
        if (optionalBook.isPresent()) {
            return Optional.of(bookMapper.toForModificationDto(
                    bookRepository.saveAndFlush(bookMapper.toBook(bookDto))
            ));
        } else {
            return Optional.empty();
        }
    }

    public Optional<BookForModificationDto> updatePart(BookForModificationDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getId());
        if (optionalBook.isPresent()) {
            Book book = bookPatchMapper.updateBookFromDto(bookDto, optionalBook.get());
            return Optional.of(bookMapper.toForModificationDto(
                    bookRepository.saveAndFlush(book)
            ));
        } else {
            return Optional.empty();
        }
    }

    public Page<BookForDisplayDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toForDisplayDto);
    }

    public Optional<BookForDisplayDto> findById(UUID id) {
        return bookRepository.findById(id).map(bookMapper::toForDisplayDto);
    }

    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    public void setStatus(BookStatus status, UUID id) {
        bookRepository.setStatus(status, id);
    }

    public void addAuthorToBook(UUID bookId, UUID authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (authorOptional.isPresent() && bookOptional.isPresent()) {
            bookOptional.get().addAuthor(authorOptional.get());
            bookRepository.save(bookOptional.get());
        }
    }
}
