package org.bookworm.library.repositories;

import org.bookworm.library.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface IAuthorRepository extends JpaRepository<Author, UUID> {

    Page<Author> findAll(@NotNull Pageable pageable);

    Optional<Author> findById(UUID id);

    Author save(@NotNull Author author);

    void deleteById(UUID id);
}
