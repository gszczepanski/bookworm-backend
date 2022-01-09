package org.bookworm.library.karate;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@SqlGroup({@Sql("classpath:make_tables_empty.sql")})
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
