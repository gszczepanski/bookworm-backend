package org.bookworm.library.karate;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@SqlGroup({
        @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
})
public class LibraryKarateTests {

    @Karate.Test
    Karate testAuthor() {
        return Karate.run("author.feature").relativeTo(getClass());
    }

    @Karate.Test
    Karate testBook() {
        return Karate.run("book.feature").relativeTo(getClass());
    }

    @Karate.Test
    Karate testPerson() {
        return Karate.run("person.feature").relativeTo(getClass());
    }

    @Karate.Test
    Karate testPublisher() {
        return Karate.run("publisher.feature").relativeTo(getClass());
    }
}
