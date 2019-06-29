package org.bookworm.library.repositories;

import org.bookworm.library.entities.Person;
import org.bookworm.library.entities.PersonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface IPersonRepository extends JpaRepository<Person, UUID> {

    Page<Person> findAll(@NotNull Pageable pageable);

    Optional<Person> findById(@NotNull UUID id);

    Person save(@NotNull Person person);

    void deleteById(@NotNull UUID id);

    @Modifying
    @Query("update Person p set p.type = ?1 where p.id = ?2")
    void setType(@NotNull PersonType type, @NotNull UUID id);
}
