package org.bookworm.library.repositories;

import org.bookworm.library.entities.Book;
import org.bookworm.library.entities.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/06
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Modifying
    @Query("update Book b set b.status = ?1 where b.id = ?2")
    void setStatus(@NotNull BookStatus status, @NotNull UUID id);
}
