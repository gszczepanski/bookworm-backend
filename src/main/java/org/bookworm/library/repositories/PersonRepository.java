package org.bookworm.library.repositories;

import org.bookworm.library.entities.Person;
import org.bookworm.library.entities.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Transactional
    @Modifying
    @Query("update Person p set p.type = ?1 where p.id = ?2")
    void setType(@NotNull PersonType type, @NotNull UUID id);
}
