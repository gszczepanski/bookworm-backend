package org.bookworm.library.repository;

import org.bookworm.library.entities.Book;
import org.bookworm.library.entities.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/06
 */
public interface BookRepository extends JpaRepository<Book, UUID> {

    Page<Book> findAll(Pageable pageable);

    Optional<Book> findById(UUID id);

//    Page<Book> findAllByBorrowerPersonId(Integer borrowerPersonId, Pageable pageable);

    Book save(Book book);

    void deleteById(UUID id);

    void setStatus(BookStatus status, UUID id);
}
