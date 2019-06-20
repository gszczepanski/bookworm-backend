package org.bookworm.library.repositories;

import org.bookworm.library.entities.Book;
import org.bookworm.library.entities.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/06
 */
public interface IBookRepository extends JpaRepository<Book, UUID> {

    Page<Book> findAll(@NotNull Pageable pageable);

    Optional<Book> findById(@NotNull UUID id);

//    Page<Book> findAllByBorrowerPersonId(Integer borrowerPersonId, Pageable pageable);

    Book save(@NotNull Book book);

    void deleteById(@NotNull UUID id);

    @Modifying
    @Query("update Book b set b.status = ?1 where b.id = ?2")
    void setStatus(@NotNull BookStatus status, @NotNull UUID id);
}
